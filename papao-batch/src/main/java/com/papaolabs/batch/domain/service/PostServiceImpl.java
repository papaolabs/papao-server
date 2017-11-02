package com.papaolabs.batch.domain.service;

import com.papaolabs.batch.infrastructure.feign.openapi.OpenApiClient;
import com.papaolabs.batch.infrastructure.feign.openapi.dto.AnimalDTO;
import com.papaolabs.batch.infrastructure.jpa.entity.Breed;
import com.papaolabs.batch.infrastructure.jpa.entity.Post;
import com.papaolabs.batch.infrastructure.jpa.entity.Shelter;
import com.papaolabs.batch.infrastructure.jpa.repository.BreedRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.PostRepository;
import com.papaolabs.batch.infrastructure.jpa.repository.ShelterRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.StringUtils.isAllBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    @NotNull
    private final OpenApiClient openApiClient;
    @NotNull
    private final BreedRepository breedRepository;
    @NotNull
    private final ShelterRepository shelterRepository;
    @NotNull
    private final PostRepository postRepository;
    public final static String DATE_FORMAT = "yyyyMMdd";

    public PostServiceImpl(OpenApiClient openApiClient,
                           BreedRepository breedRepository,
                           ShelterRepository shelterRepository,
                           PostRepository postRepository) {
        this.openApiClient = openApiClient;
        this.breedRepository = breedRepository;
        this.shelterRepository = shelterRepository;
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> syncPostList(String beginDate, String endDate) {
        StopWatch stopWatch = new StopWatch();
        List<Post> originalPosts = postRepository.findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual
            (convertStringToDate(beginDate),
             convertStringToDate(endDate));
        List<Post> posts = openApiClient.animal(beginDate, endDate)
                                        .stream()
                                        .map(this::transform)
                                        .map(x -> {
                                            for (Post post : originalPosts) {
                                                if (post.getDesertionId()
                                                        .equals(x.getDesertionId())) {
                                                    x.setId(post.getId());
                                                    x.setCreatedDate(post.getCreatedDate());
                                                    break;
                                                }
                                            }
                                            return x;
                                        })
                                        .collect(Collectors.toList());
        return postRepository.save(posts);
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
        log.warn("convertKindName - 예외처리 kindName : {}", kindName);
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
        return Integer.valueOf(result);
    }

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
    }
}
