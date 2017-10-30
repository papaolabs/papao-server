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

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<AnimalDTO> animals = Arrays.asList();
        try {
            animals = openApiClient.animal(beginDate, endDate);
        } catch (FeignException fe) {
            fe.printStackTrace();
        }
        List<Post> posts = openApiClient.animal(beginDate, endDate)
                                        .stream()
                                        .map(x -> {
                                            log.debug("syncPostList - AnimalDTO :: {}", x);
                                            Breed mockBreed = new Breed();
                                            mockBreed.setKindCode(-1L);
                                            Breed breed = Optional.ofNullable(breedRepository.findByKindName(convertKindName(x.getBreedName())))
                                                                  .orElse(mockBreed);
                                            String[] addressArr = x.getJurisdiction()
                                                                   .split(" ");
                                            if (addressArr.length <= 1) {
                                                addressArr = x.getShelterAddress()
                                                              .split(" ");
                                            }
                                            Shelter mockShelter = new Shelter();
                                            mockShelter.setShelterCode(-1L);
                                            Shelter shelter = Optional.ofNullable(shelterRepository
                                                                                      .findBySidoNameAndGunguNameAndShelterName(
                                                                                          addressArr[0],
                                                                                          addressArr[1],
                                                                                          x.getShelterName
                                                                                              ()))
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
                                            return post;
                                        })
                                        .collect(Collectors.toList());
        postRepository.save(posts);
        return posts;
    }

    private String convertDateToString(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        return transFormat.format(date);
    }

    private Date convertStringToDate(String from) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
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
                Float.valueOf(weight);
            } catch (NumberFormatException nfe) {
                return Float.valueOf(result);
            }
        }
        return Float.valueOf(result);
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
}
