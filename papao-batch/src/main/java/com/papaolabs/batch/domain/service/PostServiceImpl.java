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
        Map<String, Breed> kindMap = animalKindRepository.findAll()
                                                         .stream()
                                                         .collect(Collectors.toMap(Breed::getKindName, Function.identity()));
        Map<String, Shelter> shelterMap = animalShelterRepository.findAll()
                                                                 .stream()
                                                                 .collect(Collectors.toMap(x -> StringUtils.deleteWhitespace(
                                                                     StringUtils.join(x.getRegion()
                                                                                       .getSidoName(),
                                                                                      x.getRegion()
                                                                                       .getGunguName(),
                                                                                      x.getName())),
                                                                                           Function.identity()));
        Map<String, Region> regionMap = regionRepository.findAll()
                                                        .stream()
                                                        .collect(Collectors.toMap(x -> StringUtils
                                                                                      .deleteWhitespace(
                                                                                          StringUtils.join(x.getSidoName(),
                                                                                                           x.getGunguName())),
                                                                                  Function.identity()));
        Map<String, Post> postMap = animalPostRepository.findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(
            convertStringToDate(beginDate),
            convertStringToDate(endDate))
                                                        .stream()
                                                        .collect(Collectors.toMap(Post::getDesertionId, Function.identity()));
        List<AnimalDTO> animal = openApiClient.animal(beginDate, endDate);
        List<Post> results = animal.stream()
                                   .map(x -> {
                                       Breed kind = kindMap.get(convertKindName(x.getBreedName()));
                                       String[] addressArr = x.getJurisdiction()
                                                              .split(StringUtils.SPACE);
                                       if (addressArr.length <= 1) {
                                           addressArr = x.getShelterAddress()
                                                         .split(StringUtils.SPACE);
                                       }
                                       Shelter animalShelter = shelterMap.get(StringUtils.deleteWhitespace(StringUtils.join(
                                           addressArr[0],
                                           isNotEmpty(addressArr[1]) ? addressArr[1] : addressArr[0],
                                           x.getShelterName())));
                                       if (animalShelter == null) {
                                           Region region = regionMap.get(StringUtils.deleteWhitespace(StringUtils.join(addressArr[0],
                                                                                                                       isNotEmpty(
                                                                                                                           addressArr[1]) ?
                                                                                                                           addressArr[1] :
                                                                                                                           addressArr[0])));
                                           animalShelter = new Shelter();
                                           animalShelter.setId(-1L);
                                           animalShelter.setCode(-1L);
                                           animalShelter.setName(x.getShelterName());
                                           animalShelter.setRegion(region);
                                       }
                                       Image animalImage = new Image();
                                       animalImage.setUrl(x.getImageUrl());
                                       Post animalPost = new Post();
                                       try {
                                           animalPost.setAge(Integer.valueOf(x.getAge()));
                                       } catch (NumberFormatException nfe) {
                                           animalPost.setAge(-1);
                                       }
                                       animalPost.setWeight(Float.valueOf(x.getWeight()));
                                       animalPost.setGenderType(Post.GenderType.getType(x.getGenderCode()));
                                       animalPost.setNeuterType(Post.NeuterType.getType(x.getNeuterCode()));
                                       animalPost.setStateType(Post.StateType.getType(x.getStateType()));
                                       animalPost.setBreed(kind);
                                       animalPost.setPostType(Post.PostType.SYSTEM);
                                       animalPost.setDesertionId(x.getDesertionId());
                                       animalPost.setContact(x.getUserContact());
                                       animalPost.setNoticeId(x.getNoticeId());
                                       animalPost.setNoticeBeginDate(convertStringToDate(x.getNoticeBeginDate()));
                                       animalPost.setNoticeEndDate(convertStringToDate(x.getNoticeEndDate()));
                                       animalPost.setHappenDate(convertStringToDate(x.getHappenDate()));
                                       animalPost.setHappenPlace(x.getHappenPlace());
                                       animalPost.setFeature(x.getFeature());
                                       animalPost.setHelperName(x.getUserName());
                                       animalPost.setHelperContact(x.getUserContact());
                                       animalPost.setRegion(animalShelter.getRegion());
                                       animalPost.setShelter(animalShelter);
                                       animalPost.setImage(Arrays.asList(animalImage));
                                       animalPost.setHitCount(0L);
                                       return animalPost;
                                   })
                                   .map(x -> {
                                       Post post = postMap.get(x.getDesertionId());
                                       if (post != null) {
                                           x.setId(post.getId());
                                           x.setImage(post.getImage());
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
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
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
        // Todo 분기처리에 대한 대책 필요
/*        if (kindName.contains("발바리") || kindName.contains("진돗개믹스견")) {
            return "믹스견";
        }*/
        if (kindName.contains("[개] ")) {
            return kindName.replace("[개] ", "");
        }
        if (kindName.contains("[고양이]")) {
            return kindName.replace("[고양이]", "고양이");
        }
//        log.warn("convertKindName - 예외처리 kindName : {}", kindName);
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
