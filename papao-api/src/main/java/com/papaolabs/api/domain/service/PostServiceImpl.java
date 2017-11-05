package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Breed;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Shelter;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BreedRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.ShelterRepository;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import com.papaolabs.api.interfaces.v1.dto.type.StateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.StringUtils.isAllBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

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

    public PostServiceImpl(PostRepository postRepository,
                           BreedRepository breedRepository,
                           ShelterRepository shelterRepository) {
        this.postRepository = postRepository;
        this.breedRepository = breedRepository;
        this.shelterRepository = shelterRepository;
    }

    @Override
    public PostDTO create(String happenDate,
                          String happenPlace,
                          String uid,
                          String postType,
                          List<String> imageUrls,
                          String kindUpCode,
                          String kindCode,
                          String contact,
                          String gender,
                          String neuter,
                          String age,
                          Float weight,
                          String feature,
                          String uprCode,
                          String orgCode) {
        Post post = new Post();
        post.setHappenDate(convertStringToDate(happenDate));
        post.setHappenPlace(happenPlace);
        post.setManager(uid);
        post.setPostType(postType);
        post.setImageUrl(imageUrls.get(0));
        post.setAnimalCode(Long.valueOf(kindCode));
        post.setContact(contact);
        post.setGenderCode(gender);
        post.setNeuterCode(neuter);
        post.setAge(Integer.valueOf(age));
        post.setWeight(weight);
        post.setFeature(feature);
        post.setShelterCode(Long.valueOf(orgCode));
        post.setIsDisplay(TRUE);
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
        return postRepository.findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(convertStringToDate(beginDate),
                                                                                         convertStringToDate(endDate))
                             .stream()
//                             .filter(Post::getIsDisplay)
/*                             .filter(x -> isNotEmpty(kindUpCode) ? kindUpCode.equals(x.getKindUpCode()) : TRUE)
                             .filter(x -> isNotEmpty(uprCode) ? uprCode.equals(x.getUprCode()) : TRUE)
                             .filter(x -> isNotEmpty(orgCode) ? orgCode.equals(x.getOrgCode()) : TRUE)*/
                             .map((this::transform))
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
//                             .filter(Post::getIsDisplay)
                      .filter(x -> isNotEmpty(upKindCode) ? upKindCode.equals(x.getAnimalCode()) : TRUE)
                      .filter(x -> {
                          Shelter shelter = shelterRepository.findByShelterCode(String.valueOf(x.getShelterCode()));
                          return isNotEmpty(uprCode) ? uprCode.equals(shelter.getSidoName()) : TRUE;
                      })
                      .filter(x -> {
                          Shelter shelter = shelterRepository.findByShelterCode(String.valueOf(x.getShelterCode()));
                          return isNotEmpty(orgCode) ? orgCode.equals(shelter.getGunguCode()) : TRUE;
                      })
                      .map((this::transform))
                      .sorted(Comparator.comparing(PostDTO::getHappenDate))
                      .collect(Collectors.toList());
    }

    @Override
    public PostDTO readPost(String postId) {
        Post post = postRepository.findOne(Long.valueOf(postId));
        if (post == null) {
            log.debug("[NotFound] readPost - postId : {postId}", postId);
            PostDTO postDTO = new PostDTO();
            postDTO.setId(-1L);
            return postDTO;
        }
        return transform(post);
    }

    @Override
    public PostDTO delete(String postId) {
        Post post = postRepository.findOne(Long.valueOf(postId));
        if (post == null) {
            log.debug("[NotFound] delete - postId : {postId}", postId);
            PostDTO postDTO = new PostDTO();
            postDTO.setId(-1L);
            return postDTO;
        } else if (!post.getIsDisplay()) {
            log.debug("[NotValid] delete - isDisplay : {isDisplay}, postId : {postId}", post.getIsDisplay(), postId);
            PostDTO postDTO = new PostDTO();
            postDTO.setId(-1L);
            return postDTO;
        }
        post.setIsDisplay(FALSE);
        postRepository.save(post);
        return transform(post);
    }

    @Override
    public PostDTO setState(String postId, StateType state) {
        Post post = postRepository.findOne(Long.valueOf(postId));
/*        post.setState(state.name());*/
        return transform(post);
    }

    private PostDTO transform(Post post) {
        Breed breed;
        if (post.getAnimalCode() == -1L) {
            breed = breedRepository.findByKindCode(117L);
        } else {
            breed = breedRepository.findByKindCode(post.getAnimalCode());
        }
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setDesertionId(post.getDesertionId());
        postDTO.setState(post.getStateType());
        postDTO.setImageUrl(post.getImageUrl());
        postDTO.setType(post.getPostType());
        postDTO.setGender(post.getGenderCode());
        postDTO.setNeuter(post.getNeuterCode());
        postDTO.setFeature(post.getFeature());
        // Todo user 필요
/*        postDTO.setUserId();
        postDTO.setUserName();
        postDTO.setUserContact();
        postDTO.setUserAddress();*/
        postDTO.setHappenDate(convertDateToString(post.getHappenDate()));
        postDTO.setHappenPlace(post.getHappenPlace());
        postDTO.setKindUpCode(String.valueOf(breed.getSpeciesCode()));
        postDTO.setKindCode(String.valueOf(breed.getKindCode()));
        postDTO.setUserName(breed.getKindName());
        postDTO.setAge(String.valueOf(post.getAge()));
        postDTO.setWeight(String.valueOf(post.getWeight()));
        // Todo view count, favorite setting
        postDTO.setViewCount(-1L);
        postDTO.setFavorite(FALSE);
        postDTO.setCreatedDate(convertDateToString(post.getCreatedDate()));
        postDTO.setUpdatedDate(convertDateToString(post.getUpdatedDate()));
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
