package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.Post;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.AnimalApiClient;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.AnimalApiResponse;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import com.papaolabs.api.interfaces.v1.dto.PostType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class PostServiceImpl implements PostService {
    @Value("${seoul.api.animal.appKey}")
    private String appKey;
    @NotNull
    private final AnimalApiClient animalApiClient;
    @NotNull
    private final PostRepository postRepository;

    public PostServiceImpl(AnimalApiClient animalApiClient, PostRepository postRepository) {
        this.animalApiClient = animalApiClient;
        this.postRepository = postRepository;
    }

    @Override
    public Post create(String noticeBeginDate,
                       String noticeEndDate,
                       String imageUrl,
                       String thumbImageUrl,
                       String state,
                       String gender,
                       String neuterYn,
                       String description,
                       String shelterName,
                       String shelterTel,
                       String shelterAddress,
                       String department,
                       String managerName,
                       String managerTel,
                       String note,
                       String desertionNo,
                       String happenDate,
                       String happenPlace,
                       String kindCode,
                       String colorCode,
                       String age,
                       String weight) {
        Post post = new Post();
        post.setNoticeBeginDate(convertStringToDate(noticeBeginDate));
        post.setNoticeEndDate(convertStringToDate(noticeEndDate));
        post.setImageUrl(imageUrl);
        post.setThumbImageUrl(thumbImageUrl);
        post.setState(state);
        post.setGender(gender);
        post.setNeuterYn(neuterYn);
        post.setDescription(description);
        post.setShelterName(shelterName);
        post.setShelterAddress(shelterAddress);
        post.setShelterTel(shelterTel);
        post.setDepartment(department);
        post.setManagerName(managerName);
        post.setManagerTel(managerTel);
        post.setNote(note);
        post.setDesertionNo(desertionNo);
        post.setHappenDate(convertStringToDate(happenDate));
        post.setHappenPlace(convertStringToDate(happenPlace));
        post.setKindCode(kindCode);
        post.setColorCode(colorCode);
        post.setAge(Integer.valueOf(age));
        post.setWeight(Float.valueOf(weight));
        return postRepository.save(post);
    }

    @Override
    public Post update(String id,
                       String noticeBeginDate,
                       String noticeEndDate,
                       String imageUrl,
                       String thumbImageUrl,
                       String state,
                       String gender,
                       String neuterYn,
                       String description,
                       String shelterName,
                       String shelterTel,
                       String shelterAddress,
                       String department,
                       String managerName,
                       String managerTel,
                       String note,
                       String desertionNo,
                       String happenDate,
                       String happenPlace,
                       String kindCode,
                       String colorCode,
                       String age,
                       String weight) {
        Post post = new Post();
        post.setId(Long.valueOf(id));
        post.setNoticeBeginDate(convertStringToDate(noticeBeginDate));
        post.setNoticeEndDate(convertStringToDate(noticeEndDate));
        post.setImageUrl(imageUrl);
        post.setThumbImageUrl(thumbImageUrl);
        post.setState(state);
        post.setGender(gender);
        post.setNeuterYn(neuterYn);
        post.setDescription(description);
        post.setShelterName(shelterName);
        post.setShelterAddress(shelterAddress);
        post.setShelterTel(shelterTel);
        post.setDepartment(department);
        post.setManagerName(managerName);
        post.setManagerTel(managerTel);
        post.setNote(note);
        post.setDesertionNo(desertionNo);
        post.setHappenDate(convertStringToDate(happenDate));
        post.setHappenPlace(convertStringToDate(happenPlace));
        post.setKindCode(kindCode);
        post.setColorCode(colorCode);
        post.setAge(Integer.valueOf(age));
        post.setWeight(Float.valueOf(weight));
        return postRepository.save(post);
    }

    @Override
    public void delete(String id) {
        postRepository.delete(Long.valueOf(id));
    }

    @Override
    public List<PostDTO> getAnimalList(String beginDate,
                                       String endDate,
                                       String upKindCode,
                                       String kindCode,
                                       String uprCode,
                                       String orgCode,
                                       String shelterCode,
                                       String state,
                                       String pageNo,
                                       String size) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (isEmpty(beginDate)) {
            beginDate = now.format(formatter);
        }
        if (isEmpty(endDate)) {
            endDate = now.format(formatter);
        }
/*
        postRepository.filter(kindCode, uprCode, orgCode, shelterCode, state)
                      .stream()
                      .map(this::transform)
                      .collect(Collectors.toList());
*/
        return animalApiClient.animal(appKey, beginDate, endDate, upKindCode, kindCode,
                                      uprCode, orgCode, shelterCode, state, pageNo, size)
                              .getBody()
                              .getItems()
                              .getItem()
                              .stream()
                              .map(this::transform)
                              .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> readPosts(String beginDate, String endDate, String upKindCode, String uprCode, String orgCode) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (isEmpty(beginDate)) {
            beginDate = now.format(formatter);
        }
        if (isEmpty(endDate)) {
            endDate = now.format(formatter);
        }
        return animalApiClient.animal(appKey, beginDate, endDate, upKindCode, null,
                                      uprCode, orgCode, null, null, String.valueOf(1), String.valueOf(100000))
                              .getBody()
                              .getItems()
                              .getItem()
                              .stream()
                              .map(this::transform)
                              .collect(Collectors.toList());
    }

    private PostDTO transform(AnimalApiResponse.Body.Items.AnimalItemDTO animalItemDTO) {
        return PostDTO.builder().id(Long.valueOf(animalItemDTO.getDesertionNo()))
            .type(PostType.SYSTEM.getCode())
            .imageUrl(animalItemDTO.getPopfile())
            .happenDate(animalItemDTO.getHappenDt())
            .happenPlace(animalItemDTO.getHappenPlace())
            .kind(animalItemDTO.getKindCd())
            .gender(animalItemDTO.getSexCd())
            .state(animalItemDTO.getProcessState())
            .build();
    }

    private String convertDateToString(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return transFormat.format(date);
    }

    private Date convertStringToDate(String from) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return transFormat.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
