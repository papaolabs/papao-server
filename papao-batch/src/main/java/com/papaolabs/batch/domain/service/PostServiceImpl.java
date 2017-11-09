package com.papaolabs.batch.domain.service;

import com.papaolabs.batch.infrastructure.feign.openapi.OpenApiClient;
import com.papaolabs.batch.infrastructure.feign.openapi.dto.AnimalDTO;
import com.papaolabs.batch.infrastructure.jpa.entity.Image;
import com.papaolabs.batch.infrastructure.jpa.entity.Breed;
import com.papaolabs.batch.infrastructure.jpa.entity.Post;
import com.papaolabs.batch.infrastructure.jpa.entity.Shelter;
import com.papaolabs.batch.infrastructure.jpa.entity.Region;
import com.papaolabs.batch.infrastructure.jpa.repository.ImageRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.BreedRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.PostRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.ShelterRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private final ImageRepository animalImageRepository;
    @NotNull
    private final BreedRepository animalKindRepository;
    @NotNull
    private final PostRepository animalPostRepository;
    @NotNull
    private final ShelterRepository animalShelterRepository;
    @NotNull
    private final RegionRepository regionRepository;
    public final static String DATE_FORMAT = "yyyyMMdd";
    public final static String ETC_KIND_CODE = "429900";

    public PostServiceImpl(OpenApiClient openApiClient,
                           ImageRepository animalImageRepository,
                           BreedRepository animalKindRepository,
                           PostRepository animalPostRepository,
                           ShelterRepository animalShelterRepository,
                           RegionRepository regionRepository) {
        this.openApiClient = openApiClient;
        this.animalImageRepository = animalImageRepository;
        this.animalKindRepository = animalKindRepository;
        this.animalPostRepository = animalPostRepository;
        this.animalShelterRepository = animalShelterRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public void syncPostList(String beginDate, String endDate) {
        log.info("[syncPostList] startDate : {}, endDate : {}", beginDate, endDate);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Map<String, Breed> breedMap = animalKindRepository.findAll()
                                                          .stream()
                                                          .collect(Collectors.toMap(Breed::getKindName, Function.identity()));
        Map<String, Shelter> shelterMap = animalShelterRepository.findAll()
                                                                 .stream()
                                                                 .collect(Collectors.toMap(x -> StringUtils.deleteWhitespace(
                                                                     StringUtils.join(x.getSidoName(),
                                                                                      x.getGunguName(),
                                                                                      x.getShelterName())),
                                                                                           Function.identity()));
        Map<String, Post> postMap = animalPostRepository.findByHappenDate(
            convertStringToDate(beginDate),
            convertStringToDate(endDate))
                                                        .stream()
                                                        .collect(Collectors.toMap(Post::getDesertionId, Function.identity()));
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
                                       post.setHelperName(x.getUserName());
                                       post.setHelperContact(x.getUserContact());
                                       post.setAge(convertAge(x.getAge()));
                                       post.setWeight(convertWeight(x.getWeight()));
                                       post.setHitCount(0L);
                                       post.setIsDisplay(TRUE);
                                       // Breed 세팅
                                       String breedName = convertKindName(x.getBreedName());
                                       Breed breed = breedMap.get(breedName);
                                       post.setBreedCode(breed.getKindCode());
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
                                       if (shelter == null) {
                                           Region region = regionMap.get(StringUtils.deleteWhitespace(StringUtils.join(
                                               address[0],
                                               address.length > 1 ? (isNotEmpty(address[1]) ? address[1] : address[0]) : address[0])));
                                           shelter = new Shelter();
                                           shelter.setSidoCode(region.getSidoCode());
                                           shelter.setSidoCode(region.getGunguCode());
                                           shelter.setShelterCode(-1L);
                                       }
                                       post.setSidoCode(shelter.getSidoCode());
                                       post.setGunguCode(shelter.getGunguCode());
                                       post.setShelterCode(shelter.getShelterCode());
                                       // Image 세팅
                                       Image image = new Image();
                                       image.setUrl(x.getImageUrl());
                                       post.setImages(Arrays.asList(image));
                                       return post;
                                   })
                                   .map(x -> {
                                       Post post = postMap.get(x.getDesertionId());
                                       if (post != null) {
                                           x.setId(post.getId());
                                       }
                                       return x;
                                   })
                                   .collect(Collectors.toList());
        animalPostRepository.save(results);
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

    private Date convertStringToDate(String from) {
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
            Breed breed = animalKindRepository.findByKindName(result);
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
/*
    private Post transform(AnimalDTO animalDTO) {
        Breed mockBreed = new Breed();
        mockBreed.setKindCode(-1L);
        Breed breed = Optional.ofNullable(breedRepository.findByKindName(convertKindName(animalDTO.getBreedName())))
                              .orElse(mockBreed);
        String[] addressArr = animalDTO.getJurisdiction()
                                       .split(" ");
        if (addressArr.length <= 1) {
            addressArr = animalDTO.getShelterAddress()
                                  .split(" ");
        }
        Shelter mockShelter = new Shelter();
        mockShelter.setShelterCode(-1L);
        Shelter shelter = Optional.ofNullable(shelterRepository
                                                  .findBySidoNameAndGunguNameAndShelterName(
                                                      addressArr[0],
                                                      addressArr[1],
                                                      animalDTO.getShelterName
                                                          ()))
                                  .orElse(mockShelter);
        Post post = new Post();
        post.setNoticeId(animalDTO.getNoticeId());
        post.setNoticeBeginDate(convertStringToDate(animalDTO.getNoticeBeginDate()));
        post.setNoticeEndDate(convertStringToDate(animalDTO.getNoticeEndDate()));
        post.setDesertionId(animalDTO.getDesertionId());
        post.setStateType(animalDTO.getStateType());
        post.setImageUrl(animalDTO.getImageUrl());
        post.setAnimalCode(breed.getKindCode());
        post.setAge(convertAge(animalDTO.getAge()));
        post.setWeight(convertWeight(animalDTO.getWeight()));
        post.setGenderCode(animalDTO.getGenderCode());
        post.setNeuterCode(animalDTO.getNeuterCode());
        post.setShelterCode(shelter.getShelterCode());
        post.setShelterContact(animalDTO.getShelterContact());
        post.setManager(animalDTO.getUserName());
        post.setHelperContact(animalDTO.getUserContact());
        post.setFeature(animalDTO.getFeature());
        post.setHappenDate(convertStringToDate(animalDTO.getHappenDate()));
        post.setHappenPlace(animalDTO.getHappenPlace());
        post.setIsDisplay(TRUE);
        post.setPostType("01");
        return post;
    }*/
}
