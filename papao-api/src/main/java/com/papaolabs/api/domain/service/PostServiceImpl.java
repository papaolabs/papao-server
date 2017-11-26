package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.service.util.KorStringUtils;
import com.papaolabs.api.infrastructure.feign.openapi.PushApiClient;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Bookmark;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Breed;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Image;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.QPost;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Region;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Shelter;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BookmarkRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BreedRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.CommentRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.RegionRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.ShelterRepository;
import com.papaolabs.api.interfaces.v1.controller.response.PostDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PostPreviewDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PostRankingDTO;
import com.papaolabs.api.interfaces.v1.controller.response.ResponseType;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Value("${seoul.api.animal.appKey}")
    private String appKey;
    private static final String DATE_FORMAT = "yyyyMMdd";
    @NotNull
    private final PostRepository postRepository;
    @NotNull
    private final RegionRepository regionRepository;
    @NotNull
    private final BreedRepository breedRepository;
    @NotNull
    private final ShelterRepository shelterRepository;
    @NotNull
    private final CommentRepository commentRepository;
    @NotNull
    private final BookmarkService bookmarkService;
    @NotNull
    private final BookmarkRepository bookmarkRepository;
    @NotNull
    private final PushApiClient pushApiClient;

    public PostServiceImpl(PostRepository postRepository,
                           RegionRepository regionRepository,
                           BreedRepository breedRepository,
                           ShelterRepository shelterRepository,
                           CommentRepository commentRepository,
                           BookmarkService bookmarkService,
                           BookmarkRepository bookmarkRepository,
                           PushApiClient pushApiClient) {
        this.postRepository = postRepository;
        this.regionRepository = regionRepository;
        this.breedRepository = breedRepository;
        this.shelterRepository = shelterRepository;
        this.commentRepository = commentRepository;
        this.bookmarkService = bookmarkService;
        this.bookmarkRepository = bookmarkRepository;
        this.pushApiClient = pushApiClient;
    }

    @Override
    public ResponseType create(String happenDate,
                               String happenPlace,
                               String uid,
                               String postType,
                               String stateType,
                               List<String> imageUrls,
                               Long kindUpCode,
                               Long kindCode,
                               String contact,
                               String gender,
                               String neuter,
                               Integer age,
                               Float weight,
                               String feature,
                               Long sidoCode,
                               Long gunguCode) {
        Post post = new Post();
        post.setUid(uid);
        post.setHappenDate(convertStringToDate(happenDate));
        post.setHappenPlace(happenPlace);
        post.setPostType(Post.PostType.getType(postType));
        post.setImages(imageUrls.stream()
                                .map(x -> {
                                    Image image = new Image();
                                    image.setUrl(x);
                                    return image;
                                })
                                .collect(Collectors.toList()));
        Breed breed = breedRepository.findByKindCode(Long.valueOf(kindCode));
        post.setUpKindCode(breed.getUpKindCode());
        post.setKindCode(breed.getKindCode());
        post.setKindName(breed.getKindName());
        post.setHelperContact(contact);
        post.setGenderType(Post.GenderType.getType(gender));
        post.setNeuterType(Post.NeuterType.getType(neuter));
        post.setStateType(Post.StateType.getType(stateType));
        post.setAge(age);
        post.setWeight(weight);
        post.setFeature(feature);
        Region region = regionRepository.findBySidoCodeAndGunguCode(sidoCode, gunguCode);
        post.setHappenSidoCode(region.getSidoCode());
        post.setHappenGunguCode(region.getGunguCode());
        Shelter shelter = shelterRepository.findByShelterCode(-1L);
        post.setShelterCode(shelter.getShelterCode());
        post.setShelterName(shelter.getShelterName());
        post.setShelterContact(contact);
        post.setDisplay(TRUE);
        PostDTO postDTO = transform(postRepository.save(post));
        if (Post.PostType.getType(postType) == Post.PostType.ROADREPORT) {
            KorStringUtils korStringUtils = new KorStringUtils();
            String message = korStringUtils.append("[길거리제보] ")
                                           .append(post.getKindName())
                                           .appendJosa("가")
                                           .append(StringUtils.join(StringUtils.SPACE, postDTO.getSidoName(),
                                                                    StringUtils.SPACE,
                                                                    postDTO.getGunguName(), "에서"))
                                           .append(" 발견되었습니다\\ud83d\\udc3e")
                                           .toString();
            pushApiClient.sendPush("ALARM", "-9999", message, String.valueOf(postDTO.getId()));
        }
        return ResponseType.builder()
                           .code(ResponseType.ResponseCode.SUCCESS.getCode())
                           .name(ResponseType.ResponseCode.SUCCESS.name())
                           .build();
    }

    @Override
    public PostPreviewDTO readPostsByPage(List<String> postType,
                                          String userId,
                                          String beginDate,
                                          String endDate,
                                          String upKindCode,
                                          String kindCode,
                                          String uprCode,
                                          String orgCode,
                                          String genderType,
                                          String neuterType,
                                          String page,
                                          String size) {
        if (isEmpty(beginDate)) {
            beginDate = getDefaultDate(DATE_FORMAT);
        }
        if (isEmpty(endDate)) {
            endDate = getDefaultDate(DATE_FORMAT);
        }
        Map<Long, Shelter> shelterMap = shelterRepository.findAll()
                                                         .stream()
                                                         .collect(Collectors.toMap(x -> x.getShelterCode(),
                                                                                   Function.identity()));
        Map<Long, Breed> breedMap = breedRepository.findAll()
                                                   .stream()
                                                   .collect(Collectors.toMap(Breed::getKindCode, Function.identity()));
        PageRequest pageRequest = new PageRequest(Integer.valueOf(page),
                                                  Integer.valueOf(size),
                                                  new Sort(Sort.Direction.DESC, "happenDate"));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Page<Post> results = postRepository.findAll(generateQuery(postType,
                                                                  userId,
                                                                  beginDate,
                                                                  endDate,
                                                                  upKindCode,
                                                                  kindCode,
                                                                  uprCode,
                                                                  orgCode,
                                                                  genderType,
                                                                  neuterType),
                                                    pageRequest);
        stopWatch.stop();
        log.debug("query get time :: {} ", stopWatch.getLastTaskTimeMillis());
        PostPreviewDTO postPreviewDTO = new PostPreviewDTO();
        postPreviewDTO.setTotalElements(results.getTotalElements());
        postPreviewDTO.setTotalPages(results.getTotalPages());
        postPreviewDTO.setCurrentPage(Integer.valueOf(page));
        postPreviewDTO.setElements(results.getContent()
                                          .stream()
                                          .map(post -> {
                                              PostPreviewDTO.Element element = new PostPreviewDTO.Element();
                                              element.setId(post.getId());
                                              element.setPostType(post.getPostType());
                                              element.setStateType(post.getStateType());
                                              element.setGenderType(post.getGenderType());
                                              element.setHappenDate(convertDateToString(post.getHappenDate()));
                                              element.setHitCount(post.getHitCount());
                                              element.setCreatedDate(post.getCreatedDateTime()
                                                                         .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                              element.setUpdatedDate(post.getLastModifiedDateTime()
                                                                         .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                              stopWatch.start();
                                              Image image = post.getImages()
                                                                .get(0);
                                              PostPreviewDTO.Element.ImageUrl imageUrl = new PostPreviewDTO.Element.ImageUrl();
                                              imageUrl.setKey(image.getId());
                                              imageUrl.setUrl(image.getUrl());
                                              element.setImageUrls(Arrays.asList(imageUrl));
                                              stopWatch.stop();
                                              log.debug("transform time :: {} ", stopWatch.getLastTaskTimeMillis());
                                              // Comment 세팅
                                              element.setCommentCount(post.getComments()
                                                                          .size());
                                              // Breed 세팅
                                              Breed breed = breedMap.get(post.getKindCode());
                                              element.setKindName(breed.getKindName());
                                              // Region/Shelter 세팅
                                              Shelter shelter = shelterMap.get(post.getShelterCode());
                                              element.setHappenPlace(StringUtils.join(shelter.getSidoName(),
                                                                                      SPACE,
                                                                                      shelter.getGunguName()));
                                              return element;
                                          })
                                          .sorted(Comparator.comparing(PostPreviewDTO.Element::getHappenDate))
                                          .collect(Collectors.toList()));
        return postPreviewDTO;
    }

    private BooleanBuilder generateQuery(List<String> postType,
                                         String userId,
                                         String beginDate,
                                         String endDate,
                                         String upKindCode,
                                         String kindCode,
                                         String uprCode,
                                         String orgCode,
                                         String genderType,
                                         String neuterType) {
        QPost post = QPost.post;
        BooleanBuilder builder = new BooleanBuilder().and(post.happenDate.between(convertStringToDate(beginDate),
                                                                                  convertStringToDate(endDate))
                                                                         .and(post.isDisplay.eq(TRUE)));
        if (postType != null) {
            if (postType.size() > 0) {
                builder.and(post.postType.eq(Post.PostType.getType(postType.get(0))));
                if (postType.size() > 1) {
                    for (int i = 1; i < postType.size(); i++) {
                        builder.or(post.postType.eq(Post.PostType.getType(postType.get(i))));
                    }
                }
            }
        }
        if (isNotEmpty(userId)) {
            builder.and(post.uid.eq(userId));
        }
        if (isNotEmpty(uprCode)) {
            builder.and(post.happenSidoCode.eq(Long.valueOf(uprCode)));
        }
        if (isNotEmpty(orgCode)) {
            builder.and(post.happenGunguCode.eq(Long.valueOf(orgCode)));
        }
        if (isNotEmpty(upKindCode)) {
            builder.and(post.upKindCode.eq(Long.valueOf(upKindCode)));
        }
        if (isNotEmpty(kindCode)) {
            builder.and(post.kindCode.eq(Long.valueOf(kindCode)));
        }
        if (isNotEmpty(genderType)) {
            builder.and(post.genderType.eq(Post.GenderType.getType(genderType)));
        }
        if (isNotEmpty(neuterType)) {
            builder.and(post.neuterType.eq(Post.NeuterType.getType(neuterType)));
        }
        return builder;
    }

    @Override
    public PostDTO readPost(String postId) {
        Post post = postRepository.findOne(Long.valueOf(postId));
        if (post == null) {
            log.debug("[NotFound] readPost - id : {id}", postId);
            PostDTO postDTO = new PostDTO();
            postDTO.setId(-1L);
            return postDTO;
        }
        Long hitCount = post.getHitCount() + 1;
        post.setHitCount(hitCount);
        postRepository.save(post);
        return transform(post);
    }

    @Override
    public ResponseType delete(String postId, String userId) {
        Post post = postRepository.findOne(Long.valueOf(postId));
        if (post == null) {
            log.debug("[NotFound] delete - id : {id}", postId);
            return ResponseType.builder()
                               .code(ResponseType.ResponseCode.NOTFOUND.getCode())
                               .name(ResponseType.ResponseCode.NOTFOUND.name())
                               .build();
        } else if (post.getDisplay() == FALSE) {
            log.debug("[NotValid] delete - isDisplay : {isDisplay}, id : {id}", post.getDisplay(), postId);
            return ResponseType.builder()
                               .code(ResponseType.ResponseCode.FAIL.getCode())
                               .name(ResponseType.ResponseCode.FAIL.name())
                               .build();
        }
        post.setDisplay(FALSE);
        postRepository.save(post);
        return ResponseType.builder()
                           .code(ResponseType.ResponseCode.SUCCESS.getCode())
                           .name(ResponseType.ResponseCode.SUCCESS.name())
                           .build();
    }

    @Override
    public ResponseType setState(String postId, String userId, Post.StateType state) {
        Post post = postRepository.findOne(Long.valueOf(postId));
        post.setStateType(state);
        KorStringUtils korStringUtils = new KorStringUtils();
        if (state != Post.StateType.PROCESS) {
            String stateCode = post.getStateType()
                                   .getCode();
            stateCode = stateCode.replace("(", "");
            stateCode = stateCode.replace(")", "");
            stateCode = stateCode.replace("종료", "");
            String emoji = "";
            if (state == Post.StateType.ADOPTION) {
                emoji = "\\ud83c\\udf89";
            } else if (state == Post.StateType.EUTHANASIA) {
                emoji = "▶◀";
            } else if (state == Post.StateType.NATURALDEATH) {
                emoji = "▶◀";
            } else if (state == Post.StateType.RETURN) {
                emoji = "\\ud83d\\udc36";
            }
            String message = korStringUtils.append("북마크한 ")
                                           .append(post.getKindName())
                                           .appendJosa("이")
                                           .append(" ")
                                           .append(stateCode)
                                           .append("되었습니다")
                                           .append(emoji)
                                           .toString();
            List<Bookmark> bookmarks = bookmarkRepository.findByPostId(Long.valueOf(postId));
            for (Bookmark bookmark : bookmarks) {
                pushApiClient.sendPush("ALARM", String.valueOf(bookmark.getUserId()), message, postId);
            }
        }
        postRepository.save(post);
        return ResponseType.builder()
                           .code(ResponseType.ResponseCode.SUCCESS.getCode())
                           .name(ResponseType.ResponseCode.SUCCESS.name())
                           .build();
    }

    @Override
    public PostRankingDTO readPostRanking(String beginDate, String endDate) {
        if (isEmpty(beginDate)) {
            beginDate = getDefaultDate(DATE_FORMAT);
        }
        if (isEmpty(endDate)) {
            endDate = getDefaultDate(DATE_FORMAT);
        }
        Map<Long, Shelter> shelterMap = shelterRepository.findAll()
                                                         .stream()
                                                         .collect(Collectors.toMap(x -> x.getShelterCode(),
                                                                                   Function.identity()));
        Map<Long, Breed> breedMap = breedRepository.findAll()
                                                   .stream()
                                                   .collect(Collectors.toMap(Breed::getKindCode, Function.identity()));
        BooleanBuilder booleanBuilder = this.generateQuery(null, null, beginDate, endDate, null, null, null, null, null, null);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Iterable<Post> results = postRepository.findAll(booleanBuilder);
        stopWatch.stop();
        log.debug("readPostRanking time :: {} ", stopWatch.getLastTaskTimeMillis());
        PostRankingDTO postRankingDTO = new PostRankingDTO();
        postRankingDTO.setBeginDate(beginDate);
        postRankingDTO.setEndDate(endDate);
        List<PostRankingDTO.Element> systemList = new ArrayList<>();
        List<PostRankingDTO.Element> protectingList = new ArrayList<>();
        List<PostRankingDTO.Element> roadReportList = new ArrayList<>();
        List<PostRankingDTO.Element> missingList = new ArrayList<>();
        Map<Post.PostType, List<PostRankingDTO.Element>> elementsMap = new HashMap<>();
        List<PostRankingDTO.Element> elements = StreamSupport.stream(results.spliterator(), false)
                                                             .map(post -> {
                                                                 PostRankingDTO.Element element = new PostRankingDTO.Element();
                                                                 element.setId(post.getId());
                                                                 element.setPostType(post.getPostType());
                                                                 element.setStateType(post.getStateType());
                                                                 element.setGenderType(post.getGenderType());
                                                                 element.setHappenDate(convertDateToString(post.getHappenDate()));
                                                                 element.setHitCount(post.getHitCount());
                                                                 element.setCreatedDate(post.getCreatedDateTime()
                                                                                            .format(DateTimeFormatter.ofPattern(
                                                                                                "yyyy-MM-dd HH:mm:ss")));
                                                                 element.setUpdatedDate(post.getLastModifiedDateTime()
                                                                                            .format(DateTimeFormatter.ofPattern(
                                                                                                "yyyy-MM-dd HH:mm:ss")));
                                                                 Image image = post.getImages()
                                                                                   .get(0);
                                                                 PostPreviewDTO.Element.ImageUrl imageUrl = new PostPreviewDTO.Element
                                                                     .ImageUrl();
                                                                 imageUrl.setKey(image.getId());
                                                                 imageUrl.setUrl(image.getUrl());
                                                                 element.setImageUrls(Arrays.asList(imageUrl));
                                                                 stopWatch.start();
                                                                 element.setBookmarkCount(post.getBookmarks()
                                                                                              .size());
                                                                 // Comment 세팅
                                                                 stopWatch.stop();
                                                                 log.debug("readPostRanking ...1 :: {} ",
                                                                           stopWatch.getLastTaskTimeMillis());
                                                                 stopWatch.start();
                                                                 element.setCommentCount(post.getComments()
                                                                                             .size());
                                                                 stopWatch.stop();
                                                                 log.debug("readPostRanking ...2 :: {} ",
                                                                           stopWatch.getLastTaskTimeMillis());
                                                                 // Breed 세팅
                                                                 Breed breed = breedMap.get(post.getKindCode());
                                                                 element.setKindName(breed.getKindName());
                                                                 // Region/Shelter 세팅
                                                                 Shelter shelter = shelterMap.get(post.getShelterCode());
                                                                 element.setHappenPlace(StringUtils.join(shelter.getSidoName(),
                                                                                                         SPACE,
                                                                                                         shelter.getGunguName()));
                                                                 Integer score = Math.toIntExact(element.getHitCount() + (element
                                                                     .getCommentCount() *
                                                                     10) + element.getBookmarkCount());
                                                                 element.setScore(score);
                                                                 return element;
                                                             })
                                                             .sorted(Comparator.comparing(PostRankingDTO.Element::getScore))
                                                             .map(x -> {
                                                                 if (x.getPostType() == Post.PostType.SYSTEM) {
                                                                     systemList.add(x);
                                                                 } else if (x.getPostType() == Post.PostType.PROTECTING) {
                                                                     protectingList.add(x);
                                                                 } else if (x.getPostType() == Post.PostType.ROADREPORT) {
                                                                     roadReportList.add(x);
                                                                 } else if (x.getPostType() == Post.PostType.MISSING) {
                                                                     missingList.add(x);
                                                                 }
                                                                 return x;
                                                             })
                                                             .collect(Collectors.toList());
        postRankingDTO.setElementsMap(elementsMap);
        return postRankingDTO;
    }

    private PostDTO transform(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setDesertionId(post.getDesertionId());
        postDTO.setStateType(post.getStateType());
        postDTO.setImageUrls(post.getImages()
                                 .stream()
                                 .map(x -> {
                                     PostDTO.ImageUrl imageUrl = new PostDTO.ImageUrl();
                                     imageUrl.setKey(x.getId());
                                     imageUrl.setUrl(x.getUrl());
                                     return imageUrl;
                                 })
                                 .collect(Collectors.toList()));
        postDTO.setPostType(post.getPostType());
        postDTO.setGenderType(post.getGenderType());
        postDTO.setNeuterType(post.getNeuterType());
        postDTO.setFeature(post.getFeature());
        postDTO.setHappenDate(convertDateToString(post.getHappenDate()));
        postDTO.setHappenPlace(post.getHappenPlace());
        postDTO.setManagerName(post.getHelperName());
        postDTO.setManagerContact(post.getHelperContact());
        postDTO.setAge(post.getAge());
        postDTO.setWeight(post.getWeight());
        postDTO.setHitCount(post.getHitCount());
        postDTO.setCreatedDate(post.getCreatedDateTime()
                                   .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        postDTO.setUpdatedDate(post.getLastModifiedDateTime()
                                   .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // Breed 세팅
        Breed breed = breedRepository.findByKindCode(post.getKindCode());
        // Region/Shelter 세팅
        Region region = regionRepository.findBySidoCodeAndGunguCode(post.getHappenSidoCode(), post.getHappenGunguCode());
        Shelter shelter = shelterRepository.findByShelterCode(post.getShelterCode());
        // Todo User 세팅
        postDTO.setUpKindName(breed.getUpKindName());
        postDTO.setKindName(breed.getKindName());
        postDTO.setSidoName(region.getSidoName());
        postDTO.setGunguName(region.getGunguName());
        postDTO.setShelterName(shelter.getShelterName());
        postDTO.setBookmarkCount(bookmarkService.countBookmark(String.valueOf(post.getId())));
        return postDTO;
    }

    private String getDefaultDate(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }

    private String convertDateToString(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat(DATE_FORMAT);
        return transFormat.format(date);
    }

    private Date convertStringToDate(String from) {
        SimpleDateFormat transFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return transFormat.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}