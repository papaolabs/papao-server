package com.papaolabs.batch.domain.service;

import com.papaolabs.batch.domain.service.util.KorStringUtils;
import com.papaolabs.batch.infrastructure.feign.openapi.OpenApiClient;
import com.papaolabs.batch.infrastructure.feign.openapi.PushApiClient;
import com.papaolabs.batch.infrastructure.feign.openapi.dto.AnimalDTO;
import com.papaolabs.batch.infrastructure.jpa.entity.Bookmark;
import com.papaolabs.batch.infrastructure.jpa.entity.Breed;
import com.papaolabs.batch.infrastructure.jpa.entity.Image;
import com.papaolabs.batch.infrastructure.jpa.entity.Post;
import com.papaolabs.batch.infrastructure.jpa.entity.Region;
import com.papaolabs.batch.infrastructure.jpa.entity.Shelter;
import com.papaolabs.batch.infrastructure.jpa.repository.BookmarkRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.BreedRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.ImageRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.PostRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.RegionRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.ShelterRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.StringUtils.LF;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isAllBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    @NotNull
    private final OpenApiClient openApiClient;
    @NotNull
    private final ImageRepository imageRepository;
    @NotNull
    private final BreedRepository breedRepository;
    @NotNull
    private final PostRepository postRepository;
    @NotNull
    private final ShelterRepository shelterRepository;
    @NotNull
    private final RegionRepository regionRepository;
    @NotNull
    private final BookmarkRepository bookmarkRepository;
    @NotNull
    private final PushApiClient pushApiClient;
    public final static String BATCH_USER_ID = "9999";
    public final static String DATE_FORMAT = "yyyyMMdd";
    public final static String ETC_KIND_CODE = "429900";

    public PostServiceImpl(OpenApiClient openApiClient,
                           ImageRepository imageRepository,
                           BreedRepository breedRepository,
                           PostRepository postRepository,
                           ShelterRepository shelterRepository,
                           RegionRepository regionRepository,
                           BookmarkRepository bookmarkRepository,
                           PushApiClient pushApiClient) {
        this.openApiClient = openApiClient;
        this.imageRepository = imageRepository;
        this.breedRepository = breedRepository;
        this.postRepository = postRepository;
        this.shelterRepository = shelterRepository;
        this.regionRepository = regionRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.pushApiClient = pushApiClient;
    }

    @Override
    public void syncPostList(String beginDate, String endDate) {
        log.info("[syncPostList] startDate : {}, endDate : {}", beginDate, endDate);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Map<String, Breed> breedMap = breedRepository.findAll()
                                                     .stream()
                                                     .collect(Collectors.toMap(Breed::getKindName, Function.identity()));
        Map<String, Shelter> shelterMap = shelterRepository.findAll()
                                                           .stream()
                                                           .collect(Collectors.toMap(x -> StringUtils.deleteWhitespace(
                                                               StringUtils.join(x.getSidoName(),
                                                                                x.getGunguName(),
                                                                                x.getShelterName())),
                                                                                     Function.identity()));
        Map<String, Region> regionMap = regionRepository.findAll()
                                                        .stream()
                                                        .collect(Collectors.toMap(x -> StringUtils
                                                                                      .deleteWhitespace(
                                                                                          StringUtils.join(x.getSidoName(),
                                                                                                           x.getGunguName())),
                                                                                  Function.identity()));
        List<AnimalDTO> animal = openApiClient.animal(beginDate, endDate);
        List<Post> results = animal.stream()
                                   .map(x -> {
                                       Post post = new Post();
                                       post.setUid(BATCH_USER_ID);
                                       post.setPostType(Post.PostType.SYSTEM);
                                       post.setGenderType(Post.GenderType.getType(x.getGenderCode()));
                                       post.setNeuterType(Post.NeuterType.getType(x.getNeuterCode()));
                                       post.setStateType(Post.StateType.getType(x.getStateType()));
                                       post.setDesertionId(x.getDesertionId());
                                       post.setShelterContact(x.getShelterContact());
                                       post.setNoticeId(x.getNoticeId());
                                       post.setNoticeBeginDate(convertStringToDate(x.getNoticeBeginDate()));
                                       post.setNoticeEndDate(convertStringToDate(x.getNoticeEndDate()));
                                       post.setHappenDate(convertStringToDate(x.getHappenDate()));
                                       post.setHappenPlace(x.getHappenPlace());
                                       post.setFeature(x.getFeature());
                                       post.setHelperName(Optional.of(x.getUserName()).orElse("알수없음"));
                                       post.setHelperContact(Optional.of(x.getUserContact()).orElse("알수없음"));
                                       post.setAge(convertAge(x.getAge()));
                                       post.setWeight(convertWeight(x.getWeight()));
                                       post.setHitCount(0L);
                                       post.setDisplay(TRUE);
                                       // Breed 세팅
                                       String breedName = convertKindName(x.getBreedName());
                                       Breed breed = breedMap.get(breedName);
                                       post.setUpKindCode(breed.getUpKindCode());
                                       post.setKindCode(breed.getKindCode());
                                       post.setKindName(breed.getKindName());
                                       if (ETC_KIND_CODE.equals(breed.getUpKindCode())) {
                                           post.setFeature(StringUtils.join(x.getBreedName(), LF, post.getFeature()));
                                       }
                                       // Region / Shelter 세팅
                                       String[] address = x.getJurisdiction()
                                                           .split(SPACE);
                                       Shelter shelter = shelterMap.get(StringUtils.deleteWhitespace(StringUtils.join(
                                           address[0],
                                           address.length > 1 ? (isNotEmpty(address[1]) ? address[1] : address[0]) : address[0],
                                           x.getShelterName())));
                                       Region region = regionMap.get(StringUtils.deleteWhitespace(StringUtils.join(
                                           address[0],
                                           address.length > 1 ? (isNotEmpty(address[1]) ? address[1] : address[0]) : address[0])));
                                       if (region == null) {
                                           region = regionRepository.findBySidoCodeAndGunguCode(-1L, -1L);
                                       }
                                       if (shelter == null) {
                                           shelter = shelterRepository.findByShelterCode(-1L);
                                       }
                                       post.setHappenSidoCode(region.getSidoCode());
                                       post.setHappenGunguCode(region.getGunguCode());
                                       post.setShelterCode(shelter.getShelterCode());
                                       post.setShelterName(shelter.getShelterName());
                                       // Image 세팅
                                       Image image = new Image();
                                       image.setUrl(x.getImageUrl());
                                       post.setImages(Arrays.asList(image));
                                       return post;
                                   })
                                   .map(x -> {
                                       Post post = postRepository.findByDesertionId(x.getDesertionId());
                                       if (post != null) {
                                           x.setId(post.getId());
                                           // system batch 의 경우 image update 될 일이 전혀 없음
                                           x.setImages(imageRepository.findByPostId(x.getId()));
                                           x.setComments(post.getComments());
                                           x.setBookmarks(post.getBookmarks());
                                           x.setHitCount(post.getHitCount());
                                           x.setUid(post.getUid());
                                           x.setHitCount(post.getHitCount());
                                           x.setDisplay(post.getDisplay());
                                           if (x.getStateType() != post.getStateType() && x.getStateType() != Post.StateType.PROCESS) {
                                               KorStringUtils korStringUtils = new KorStringUtils();
                                               String stateCode = x.getStateType()
                                                                   .getCode();
                                               stateCode = stateCode.replace("(", "");
                                               stateCode = stateCode.replace(")", "");
                                               stateCode = stateCode.replace("종료", "");
                                               String emoji = "";
                                               if (x.getStateType() == Post.StateType.ADOPTION) {
                                                   emoji = "\\ud83c\\udf89";
                                               } else if (x.getStateType() == Post.StateType.EUTHANASIA) {
                                                   emoji = "▶◀";
                                               } else if (x.getStateType() == Post.StateType.NATURALDEATH) {
                                                   emoji = "▶◀";
                                               } else if (x.getStateType() == Post.StateType.RETURN) {
                                                   emoji = "\\ud83d\\udc36";
                                               }
                                               String message = korStringUtils.append("북마크한 ")
                                                                              .append(post.getKindName())
                                                                              .appendJosa("이")
                                                                              .append(" ")
                                                                              .append(stateCode)
                                                                              .append(Post.StateType.NATURALDEATH.name().equals(stateCode) ? "하였습니다" : "되었습니다")
                                                                              .append(emoji)
                                                                              .toString();
                                               pushApiClient.sendPush("ALARM", "9999", message,
                                                                      String.valueOf(x.getId()));
                                               /*for (Bookmark bookmark : bookmarks) {
                                                   pushApiClient.sendPush(String.valueOf(bookmark.getUserId()), message,
                                                                          String.valueOf(x.getId()));
                                               }*/
                                           }
                                       }
                                       return x;
                                   })
                                   .sorted(Comparator.comparing(x -> x.getHappenDate()))
                                   .collect(Collectors.toList());
        postRepository.save(results);
        stopWatch.stop();
        log.info("[syncPostList_end} result size {} - executionTime : {} millis", results.size(), stopWatch.getLastTaskTimeMillis());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), TRUE) == null;
    }

    private String convertDateToString(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat(DATE_FORMAT);
        return transFormat.format(date);
    }

    private String getDefaultDate(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }

    private Date convertStringToDate(String from) {
        if(isEmpty(from)) {
            return convertStringToDate(getDefaultDate(DATE_FORMAT));
        }
        SimpleDateFormat transFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return transFormat.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    private String convertKindName(String kindName) {
        if (kindName.contains("[개] ")) {
            String result = kindName.replace("[개] ", "");
            Breed breed = breedRepository.findByKindName(result);
            return breed == null ? "믹스견" : breed.getKindName();
        }
        if (kindName.contains("[고양이]")) {
            return kindName.replace("[고양이]", "고양이");
        }
        return "기타축종";
    }

    private Float convertWeight(String weight) {
        String result = "-1";
        if (isEmpty(weight)) {
            return Float.valueOf(result);
        }
        if (weight.contains("(Kg)")) {
            result = weight.replace("(Kg)", "");
            try {
                Float.valueOf(result);
            } catch (NumberFormatException nfe) {
                return Float.valueOf(result);
            }
        }
        return Float.valueOf(weight);
    }

    private Integer convertAge(String age) {
        String result = age.replace(" ", "");
        if (isEmpty(result) || isAllBlank(result)) {
            return -1;
        }
        if (result.contains("(년생)")) {
            return Integer.valueOf(result.replace("(년생)", ""));
        }
        try {
            return Integer.valueOf(result);
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }
}
