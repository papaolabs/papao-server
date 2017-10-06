package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.Post;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.KindRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.AnimalApiClient;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.AnimalApiResponse;
import com.papaolabs.api.interfaces.v1.dto.type.GenderType;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import com.papaolabs.api.interfaces.v1.dto.type.PostType;
import com.papaolabs.api.interfaces.v1.dto.type.StateType;
import com.papaolabs.api.interfaces.v1.dto.type.YesNoType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
    private static final String UNKNOWN = "UNKNOWN";
    @Value("${seoul.api.animal.appKey}")
    private String appKey;
    @NotNull
    private final AnimalApiClient animalApiClient;
    @NotNull
    private final PostRepository postRepository;
    @NotNull
    private final KindRepository kindRepository;

    public PostServiceImpl(AnimalApiClient animalApiClient, PostRepository postRepository, KindRepository kindRepository) {
        this.animalApiClient = animalApiClient;
        this.postRepository = postRepository;
        this.kindRepository = kindRepository;
    }

    @Override
    public Post create(String imageUrl,
                       String type,
                       String gender,
                       String neuterYn,
                       String uid,
                       String contracts,
                       String happenDate,
                       String happenPlace,
                       String kindUpCode,
                       String kindCode,
                       String age,
                       String weight,
                       String introduction,
                       String feature) {
        Post post = new Post();
        post.setImageUrl(imageUrl);
        post.setType(type);
        post.setState(StateType.PROCESS.name());
        post.setGender(isEmpty(gender) ? GenderType.Q.name() : gender);
        post.setNeuter(isEmpty(neuterYn) ? YesNoType.U.name() : neuterYn);
        post.setUid(uid);
        post.setContracts(contracts);
        post.setHappenDate(convertStringToDate(happenDate));
        post.setHappenPlace(isNotEmpty(happenPlace) ? happenPlace : UNKNOWN);
        post.setKindUpCode(kindUpCode);
        post.setKindCode(isNotEmpty(kindCode) ? kindCode : StringUtils.EMPTY);
        post.setAge(isNotEmpty(age) ? Integer.valueOf(age) : -1);
        post.setWeight(isNotEmpty(weight) ? Float.valueOf(weight) : -1);
        post.setIntroduction(introduction);
        post.setFeature(feature);
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

    @Override
    public PostDTO readPost(String postId) {
        Post post = postRepository.findOne(Long.valueOf(postId));
        if(post == null) {
            log.debug("[NotFound] readPost - postId : {postId}", postId);
            return PostDTO.builder()
                          .id(-1L)
                          .build();
        }
        return transform(post);
    }

    private PostDTO transform(Post post) {
        return PostDTO.builder()
                      .id(post.getId())
                      .type(post.getType())
                      .imageUrl(post.getImageUrl())
                      .kind(post.getKindUpCode())
                      .happenDate(convertDateToString(post.getHappenDate()))
                      .happenPlace(post.getHappenPlace())
                      .contracts(post.getContracts())
                      .weight(String.valueOf(post.getWeight()))
                      .gender(post.getGender())
                      .state(post.getState())
                      .neuter(post.getNeuter())
                      .feature(post.getFeature())
                      .introduction(post.getIntroduction())
                      .build();
    }

    private PostDTO transform(AnimalApiResponse.Body.Items.AnimalItemDTO animalItemDTO) {
        return PostDTO.builder()
                      .id(Long.valueOf(animalItemDTO.getDesertionNo()))
                      .type(PostType.SYSTEM.getCode())
                      .imageUrl(animalItemDTO.getPopfile())
                      .happenDate(animalItemDTO.getHappenDt())
                      .happenPlace(animalItemDTO.getOrgNm())
                      .kind(convertKindName(animalItemDTO.getKindCd()))
                      .gender(animalItemDTO.getSexCd())
                      .state(animalItemDTO.getProcessState())
                      .build();
    }

    private String convertKindName(String kindName) {
        if (kindName.contains("[개] ")) {
            return kindName.replace("[개] ", "");
        }
        if (kindName.contains("[기타축종] ")) {
            return kindName.replace("[기타축종] ", "");
        }
        if (kindName.contains("[고양이]")) {
            return kindName.replace("[고양이]", "고양이");
        }
        return kindName;
    }

    private String convertDateToString(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        return transFormat.format(date);
    }

    private Date convertStringToDate(String from) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return transFormat.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
