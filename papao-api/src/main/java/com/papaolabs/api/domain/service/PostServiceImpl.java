package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Image;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BreedRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.RegionRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.ShelterRepository;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static org.apache.commons.lang3.StringUtils.isAllBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
@Service
@Transactional
public class PostServiceImpl implements PostService {
    private static final String MAX_SIZE = "500000";
    private static final String START_INDEX = "1";
    @Value("${seoul.api.animal.appKey}")
    private String appKey;
    private static final String UNKNOWN = "UNKNOWN";
    private static final String DATE_FORMAT = "yyyyMMdd";
    @NotNull
    private final PostRepository postRepository;
    @NotNull
    private final BreedRepository breedRepository;
    @NotNull
    private final ShelterRepository shelterRepository;
    @NotNull
    private final RegionRepository regionRepository;

    public PostServiceImpl(PostRepository postRepository,
                           BreedRepository breedRepository,
                           ShelterRepository shelterRepository,
                           RegionRepository regionRepository) {
        this.postRepository = postRepository;
        this.breedRepository = breedRepository;
        this.shelterRepository = shelterRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public PostDTO create(String happenDate,
                          String happenPlace,
                          String uid,
                          String postType,
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
//        post.setBreed(breedRepository.findByKindCode(Long.valueOf(kindCode)));
        post.setHelperContact(contact);
        post.setGenderType(Post.GenderType.getType(gender));
        post.setNeuterType(Post.NeuterType.getType(neuter));
        post.setStateType(Post.StateType.UNKNOWN);
        post.setAge(age);
        post.setWeight(weight);
        post.setFeature(feature);
//        post.setRegion(regionRepository.findBySidoCodeAndGunguCode(sidoCode, gunguCode));
        return transform(postRepository.save(post));
    }

    @Override
    public List<PostDTO> readPosts(String beginDate,
                                   String endDate,
                                   String upKindCode,
                                   String kindCode,
                                   String uprCode,
                                   String orgCode) {
        if (isEmpty(beginDate)) {
            beginDate = getDefaultDate(DATE_FORMAT);
        }
        if (isEmpty(endDate)) {
            endDate = getDefaultDate(DATE_FORMAT);
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Post> originalPosts = postRepository
            .findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(
                convertStringToDate(beginDate),
                convertStringToDate(endDate));
        stopWatch.stop();
        log.debug("originalPosts get time :: {} ", stopWatch.getLastTaskTimeMillis());
        return originalPosts
            .stream()
            .filter(Post::getDisplay)
/*                             .filter(x -> isNotEmpty(upKindCode) ? upKindCode.equals(x.getBreed()
                                                                                      .getUpKindCode().toString()) : TRUE)
                             .filter(x -> isNotEmpty(kindCode) ? kindCode.equals(x.getBreed()
                                                                                  .getKindCode().toString()) : TRUE)
                             .filter(x -> {
                                 Shelter shelter = shelterRepository.findByCode(x.getShelter()
                                                                                 .getCode());
                                 return isNotEmpty(uprCode) ? uprCode.equals(shelter.getRegion()
                                                                                    .getSidoCode().toString()) : TRUE;
                             })
                             .filter(x -> {
                                 Shelter shelter = shelterRepository.findByCode(x.getShelter()
                                                                                 .getCode());
                                 return isNotEmpty(orgCode) ? orgCode.equals(shelter.getRegion()
                                                                                    .getGunguCode().toString()) : TRUE;
                             })*/
            .map(this::transform)
            .sorted(Comparator.comparing(PostDTO::getHappenDate))
            .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> readPostsByPage(String beginDate,
                                         String endDate,
                                         String upKindCode,
                                         String kindCode,
                                         String uprCode,
                                         String orgCode,
                                         String page,
                                         String size) {
        if (isEmpty(beginDate)) {
            beginDate = getDefaultDate(DATE_FORMAT);
        }
        if (isEmpty(endDate)) {
            endDate = getDefaultDate(DATE_FORMAT);
        }
        PageRequest pageRequest = new PageRequest(Integer.valueOf(page), Integer.valueOf(size));
        Page<Post> results = postRepository
            .findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(
                convertStringToDate(beginDate),
                convertStringToDate(endDate),
                pageRequest);
        return results.getContent()
                      .stream()
                      .filter(Post::getDisplay)
/*                      .filter(x -> isNotEmpty(upKindCode) ? upKindCode.equals(x.getBreed()
                                                                               .getUpKindCode().toString()) : TRUE)
                      .filter(x -> isNotEmpty(kindCode) ? kindCode.equals(x.getBreed()
                                                                           .getKindCode().toString()) : TRUE)
                      .filter(x -> {
                          Shelter shelter = shelterRepository.findByCode(x.getShelter()
                                                                          .getCode());
                          return isNotEmpty(uprCode) ? uprCode.equals(shelter.getRegion()
                                                                             .getSidoCode().toString()) : TRUE;
                      })
                      .filter(x -> {
                          Shelter shelter = shelterRepository.findByCode(x.getShelter()
                                                                          .getCode());
                          return isNotEmpty(orgCode) ? orgCode.equals(shelter.getRegion()
                                                                             .getGunguCode().toString()) : TRUE;
                      })*/
                      .map((this::transform))
                      .sorted(Comparator.comparing(PostDTO::getHappenDate))
                      .collect(Collectors.toList());
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
    public PostDTO delete(String postId) {
        Post post = postRepository.findOne(Long.valueOf(postId));
        if (post == null) {
            log.debug("[NotFound] delete - id : {id}", postId);
            PostDTO postDTO = new PostDTO();
            postDTO.setId(-1L);
            return postDTO;
        } else if (!post.getDisplay()) {
            log.debug("[NotValid] delete - isDisplay : {isDisplay}, id : {id}", post.getDisplay(), postId);
            PostDTO postDTO = new PostDTO();
            postDTO.setId(-1L);
            return postDTO;
        }
        post.setDisplay(FALSE);
        postRepository.save(post);
        return transform(post);
    }

    @Override
    public PostDTO setState(String postId, Post.StateType state) {
        Post post = postRepository.findOne(Long.valueOf(postId));
/*        post.setState(state.helperName());*/
        return transform(post);
    }

    private PostDTO transform(Post post) {
/*        Breed breed;
        if (post.getBreed()
                .getKindCode() == -1L) {
            breed = breedRepository.findByKindCode(117L);
        } else {
            breed = breedRepository.findByKindCode(post.getBreed()
                                                       .getKindCode());
        }*/
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
        // Todo user 필요
/*        postDTO.setUserId();
        postDTO.setUserName();
        postDTO.setUserContact();
        postDTO.setUserAddress();*/
        postDTO.setHappenDate(convertDateToString(post.getHappenDate()));
        postDTO.setHappenPlace(post.getHappenPlace());
/*
        postDTO.setKindUpCode(breed.getUpKindCode());
        postDTO.setKindCode(breed.getKindCode());
*/
/*        postDTO.setKindName(post.getBreed()
                                .getKindName());
        postDTO.setSidoName(post.getRegion()
                                .getSidoName());
        postDTO.setGunguName(post.getRegion()
                                 .getGunguName());
        postDTO.setShelterName(post.getShelter()
                                   .getName());*/
        postDTO.setManagerName(post.getHelperName());
        postDTO.setManagerContact(post.getHelperContact());
        postDTO.setAge(post.getAge());
        postDTO.setWeight(post.getWeight());
        // Todo view count, favorite setting
        postDTO.setHitCount(post.getHitCount());
        postDTO.setCreatedDate(post.getCreatedDateTime()
                                   .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        postDTO.setUpdatedDate(post.getLastModifiedDateTime()
                                   .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return postDTO;
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

    private String convertWeight(String weight) {
        if (isEmpty(weight)) {
            return "-1";
        }
        if (weight.contains("(Kg)")) {
            weight = weight.replace("(Kg)", "");
            try {
                Float.valueOf(weight);
            } catch (NumberFormatException nfe) {
                weight = "-1";
            }
        }
        return weight;
    }

    private String convertAge(String age) {
        String result = age.replace(" ", "");
        if (isEmpty(result) || isAllBlank(result)) {
            return "-1";
        }
        if (result.contains("(년생)")) {
            return result.replace("(년생)", "");
        }
        return result;
    }
}
