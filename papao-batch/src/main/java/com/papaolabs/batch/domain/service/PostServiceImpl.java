package com.papaolabs.batch.domain.service;

import com.papaolabs.batch.infrastructure.feign.openapi.OpenApiClient;
import com.papaolabs.batch.infrastructure.feign.openapi.dto.AnimalDTO;
import com.papaolabs.batch.infrastructure.jpa.entity.AbandonedAnimal;
import com.papaolabs.batch.infrastructure.jpa.entity.AnimalHelper;
import com.papaolabs.batch.infrastructure.jpa.entity.AnimalImage;
import com.papaolabs.batch.infrastructure.jpa.entity.AnimalKind;
import com.papaolabs.batch.infrastructure.jpa.entity.AnimalPost;
import com.papaolabs.batch.infrastructure.jpa.entity.AnimalShelter;
import com.papaolabs.batch.infrastructure.jpa.repository.AbandonedAnimalRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.AnimalHelperRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.AnimalImageRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.AnimalKindRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.AnimalPostRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.AnimalShelterRepository;
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

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    @NotNull
    private final OpenApiClient openApiClient;
    @NotNull
    private final AbandonedAnimalRepository abandonedAnimalRepository;
    @NotNull
    private final AnimalHelperRepository animalHelperRepository;
    @NotNull
    private final AnimalImageRepository animalImageRepository;
    @NotNull
    private final AnimalKindRepository animalKindRepository;
    @NotNull
    private final AnimalPostRepository animalPostRepository;
    @NotNull
    private final AnimalShelterRepository animalShelterRepository;
    @NotNull
    private final RegionRepository regionRepository;
    public final static String DATE_FORMAT = "yyyyMMdd";

    public PostServiceImpl(OpenApiClient openApiClient,
                           AbandonedAnimalRepository abandonedAnimalRepository,
                           AnimalHelperRepository animalHelperRepository,
                           AnimalImageRepository animalImageRepository,
                           AnimalKindRepository animalKindRepository,
                           AnimalPostRepository animalPostRepository,
                           AnimalShelterRepository animalShelterRepository,
                           RegionRepository regionRepository) {
        this.openApiClient = openApiClient;
        this.abandonedAnimalRepository = abandonedAnimalRepository;
        this.animalHelperRepository = animalHelperRepository;
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
        Map<String, AnimalKind> kindMap = animalKindRepository.findAll()
                                                              .stream()
                                                              .collect(Collectors.toMap(AnimalKind::getKindName, Function.identity()));
        Map<String, AnimalShelter> shelterMap = animalShelterRepository.findAll()
                                                                       .stream()
                                                                       .collect(Collectors.toMap(x -> StringUtils.join(x.getRegion()
                                                                                                                        .getSidoName(),
                                                                                                                       x.getRegion()
                                                                                                                        .getGunguName(),
                                                                                                                       x.getName()),
                                                                                                 Function.identity()));
        Map<String, AnimalPost> postMap = animalPostRepository.findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(
            convertStringToDate(beginDate),
            convertStringToDate(endDate))
                                                              .stream()
                                                              .collect(Collectors.toMap(AnimalPost::getDesertionId, Function.identity()));
        List<AnimalDTO> animal = openApiClient.animal(beginDate, endDate);
        List<AnimalPost> results = animal.stream()
                                         .map(x -> {
                                             AnimalKind kind = kindMap.get(convertKindName(x.getBreedName()));
                                             String[] addressArr = x.getJurisdiction()
                                                                    .split(StringUtils.SPACE);
                                             if (addressArr.length <= 1) {
                                                 addressArr = x.getShelterAddress()
                                                               .split(StringUtils.SPACE);
                                             }
                                             AnimalShelter animalShelter = shelterMap.get(StringUtils.join(addressArr[0],
                                                                                                           addressArr[1],
                                                                                                           x.getShelterName()));
                                             AnimalHelper animalHelper = new AnimalHelper();
                                             animalHelper.setName(x.getUserName());
                                             animalHelper.setContact(x.getUserContact());
                                             AbandonedAnimal abandonedAnimal = new AbandonedAnimal();
                                             abandonedAnimal.setAge(Integer.valueOf(x.getAge()));
                                             abandonedAnimal.setWeight(Float.valueOf(x.getWeight()));
                                             abandonedAnimal.setGenderType(AbandonedAnimal.GenderType.getType(x.getGenderCode()));
                                             abandonedAnimal.setNeuterType(AbandonedAnimal.NeuterType.getType(x.getNeuterCode()));
                                             abandonedAnimal.setStateType(AbandonedAnimal.StateType.getType(x.getStateType()));
                                             abandonedAnimal.setKind(kind);
                                             AnimalImage animalImage = new AnimalImage();
                                             animalImage.setUrl(x.getImageUrl());
                                             AnimalPost animalPost = new AnimalPost();
                                             animalPost.setPostType(AnimalPost.PostType.SYSTEM);
                                             animalPost.setDesertionId(x.getDesertionId());
                                             animalPost.setContact(x.getUserContact());
                                             animalPost.setNoticeId(x.getNoticeId());
                                             animalPost.setNoticeBeginDate(convertStringToDate(x.getNoticeBeginDate()));
                                             animalPost.setNoticeEndDate(convertStringToDate(x.getNoticeEndDate()));
                                             animalPost.setHappenDate(convertStringToDate(x.getHappenDate()));
                                             animalPost.setHappenPlace(x.getHappenPlace());
                                             animalPost.setFeature(x.getFeature());
                                             animalPost.setAnimalHelper(animalHelper);
                                             animalPost.setRegion(animalShelter.getRegion());
                                             animalPost.setShelter(animalShelter);
                                             animalPost.setAbandonedAnimal(abandonedAnimal);
                                             animalPost.setImages(Arrays.asList(animalImage));
                                             return animalPost;
                                         })
                                         .map(x -> {
                                             AnimalPost post = postMap.get(x.getDesertionId());
                                             if (post != null) {
                                                 x.setId(post.getId());
                                             }
                                             return x;
                                         })
                                         .collect(Collectors.toList());
        animalPostRepository.save(results);
        /*animal.stream()
              .map()
              .stream()
              .map(x -> {
                  Breed mockBreed = new Breed();
                  mockBreed.setKindCode(-1L);
                  Breed breed = Optional.ofNullable(breedMap.get(convertKindName(x.getBreedName())))
                                        .orElse(mockBreed);
                  String[] addressArr = x.getJurisdiction()
                                         .split(" ");
                  if (addressArr.length <= 1) {
                      addressArr = x.getShelterAddress()
                                    .split(" ");
                  }
                  Shelter mockShelter = new Shelter();
                  mockShelter.setShelterCode(-1L);
                  Shelter shelter = Optional.ofNullable(shelterMap
                                                            .get(StringUtils.join(addressArr[0],
                                                                                  addressArr[1],
                                                                                  x.getShelterName())))
                                            .orElse(mockShelter);
                  Post post = new Post();
                  post.setNoticeId(x.getNoticeId());
                  post.setNoticeBeginDate(convertStringToDate(x.getNoticeBeginDate()));
                  post.setNoticeEndDate(convertStringToDate(x.getNoticeEndDate()));
                  post.setDesertionId(x.getDesertionId());
                  post.setStateType(x.getStateType());
                  post.setImageUrl(x.getImageUrl());
                  post.setAnimalCode(breed.getKindCode());
                  post.setAge(convertAge(x.getAge()));
                  post.setWeight(convertWeight(x.getWeight()));
                  post.setGenderCode(x.getGenderCode());
                  post.setNeuterCode(x.getNeuterCode());
                  post.setShelterCode(shelter.getShelterCode());
                  post.setShelterContact(x.getShelterContact());
                  post.setManager(x.getUserName());
                  post.setContact(x.getUserContact());
                  post.setFeature(x.getFeature());
                  post.setHappenDate(convertStringToDate(x.getHappenDate()));
                  post.setHappenPlace(x.getHappenPlace());
                  post.setIsDisplay(TRUE);
                  post.setPostType("01");
                  return post;
              })
              .map(x -> {
                  Post post = originalPosts.get(x.getDesertionId());
                  if (post != null) {
                      x.setId(post.getId());
                      x.setCreatedDate(post.getCreatedDate());
                  }*/
/*
                                              originalPostList.stream()
                                                              .filter(y -> y.getDesertionId()
                                                                            .equals(x.getDesertionId()))
                                                              .findFirst()
                                                              .ifPresent(z -> {
                                                                  x.setId(z.getId());
                                                                  x.setCreatedDate(z.getCreatedDate());
                                                              });
*//*
                                              return x;
                                          })
                                          .collect(Collectors.toList());
        postRepository.save(results);*/
/*        stopWatch.stop();
        log.info("[syncPostList_end} result size {} - executionTime : {} millis", -1, stopWatch.getLastTaskTimeMillis());*/
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
        post.setContact(animalDTO.getUserContact());
        post.setFeature(animalDTO.getFeature());
        post.setHappenDate(convertStringToDate(animalDTO.getHappenDate()));
        post.setHappenPlace(animalDTO.getHappenPlace());
        post.setIsDisplay(TRUE);
        post.setPostType("01");
        return post;
    }*/
}
