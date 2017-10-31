package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Breed;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BreedRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.ShelterRepository;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import com.papaolabs.api.interfaces.v1.dto.type.StateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private static final String DATE_FORMAT = "yyyyMMdd hh:MM:ss";
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
    public PostDTO create(String imageUrl,
                          String type,
                          String gender,
                          String neuterYn,
                          String uid,
                          String contact,
                          String happenDate,
                          String happenPlace,
                          String uprCode,
                          String orgCode,
                          String kindUpCode,
                          String kindCode,
                          String age,
                          Float weight,
                          String introduction,
                          String feature) {
        Post post = new Post();
/*        post.setImageUrl(imageUrl);
        post.setType(type);
        post.setState(StateType.PROCESS.name());
        post.setGender(isEmpty(gender) ? GenderType.Q.name() : gender);
        post.setNeuter(isEmpty(neuterYn) ? NeuterType.U.name() : neuterYn);
        post.setUserId(uid);
        post.setUserContact(contact);
        post.setHappenDate(convertStringToDate(happenDate));
        post.setHappenPlace(isNotEmpty(happenPlace) ? happenPlace : UNKNOWN);
        post.setUprCode(uprCode);
        post.setOrgCode(orgCode);
        post.setKindUpCode(kindUpCode);
        post.setKindCode(isNotEmpty(kindCode) ? kindCode : StringUtils.EMPTY);
        post.setAge(isNotEmpty(age) ? Integer.valueOf(age) : -1);
        post.setWeight(weight);
        post.setIntroduction(introduction);
        post.setFeature(feature);
        post.setIsDisplay(TRUE);*/
        return transform(postRepository.save(post));
    }

    @Override
    public List<PostDTO> readPosts(String beginDate,
                                   String endDate,
                                   String kindUpCode,
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
        return postRepository.findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(convertStringToDate(endDate),
                                                                                         convertStringToDate(beginDate),
                                                                                         pageRequest)
                             .stream()
                             .filter(Post::getIsDisplay)
/*                             .filter(x -> isNotEmpty(kindUpCode) ? kindUpCode.equals(x.getKindUpCode()) : TRUE)
                             .filter(x -> isNotEmpty(uprCode) ? uprCode.equals(x.getUprCode()) : TRUE)
                             .filter(x -> isNotEmpty(orgCode) ? orgCode.equals(x.getOrgCode()) : TRUE)*/
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

    /*public void syncPosts(String beginDate, String endDate) {
        String beginDateParam = beginDate;
        String endDateParam = endDate;
        if (isEmpty(beginDate)) {
            beginDateParam = getDefaultDate(DATE_FORMAT);
        }
        if (isEmpty(endDate)) {
            endDateParam = getDefaultDate(DATE_FORMAT);
        }
        AnimalApiResponse response = animalApiClient.animal(appKey, beginDateParam, endDateParam, EMPTY, EMPTY,
                                                            EMPTY, EMPTY, EMPTY, EMPTY, START_INDEX, MAX_SIZE);
        if (response != null) {
            List<Post> posts = postRepository.findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(convertStringToDate(beginDate),
                                                                                                         convertStringToDate(endDate));
            List<AnimalApiResponse.Body.Items.AnimalItemDTO> animalItems = response.getBody()
                                                                                   .getItems()
                                                                                   .getItem();
            log.info("AnimalItemDTO, beginDate : {}, endDate : {}, size : {}", beginDate, endDate, animalItems.size());
            postRepository.save(animalItems.stream()
                                           .map(this::transform)
                                           .map(x -> {
                                               posts.forEach(y -> {
                                                   if (y.getDesertionId()
                                                        .equals(x.getDesertionId())) {
                                                       x.setId(y.getId());
                                                       x.setCreatedDate(y.getCreatedDate());
                                                   }
                                               });
                                               return x;
                                           })
                                           .collect(Collectors.toList()));
        } else {
            log.info("PostJob, post not found.. beginDate : {}, endDate : {}", beginDate, endDate);
        }
    }*/

    /*private Post transform(AnimalApiResponse.Body.Items.AnimalItemDTO animalItemDTO) {
        String kindName = convertKindName(animalItemDTO.getKindCd());
        Kind mockKind = new Kind();
        mockKind.setUpKindCode(-1L);
        mockKind.setKindCode(-1L);
        Kind kind = Optional.ofNullable(kindRepository.findByKindName(kindName))
                            .orElse(mockKind);
        String[] orgNames = animalItemDTO.getOrgNm()
                                         .split(SPACE);
        Shelter mockShelter = new Shelter();
        mockShelter.setCityCode(-1L);
        mockShelter.setCityName(UNKNOWN);
        mockShelter.setTownCode(-1L);
        mockShelter.setTownName(UNKNOWN);
        mockShelter.setShelterCode(-1L);
        mockShelter.setShelterName(UNKNOWN);
        Shelter shelter = new Shelter();
        switch (orgNames.length) {
            case 1:
                shelter = shelterRepository.findBySidoName(orgNames[0])
                                           .stream()
                                           .findFirst()
                                           .orElse(mockShelter);
                break;
            case 2:
                shelter = shelterRepository.findByGunguName(orgNames[1])
                                           .stream()
                                           .findFirst()
                                           .orElse(mockShelter);
                break;
        }
        if ("-1".equals(shelter.getTownCode())) {
            shelter = shelterRepository.findByShelterName(animalItemDTO.getCareNm())
                                       .stream()
                                       .findFirst()
                                       .orElse(mockShelter);
        }
        Post post = new Post();
        post.setDesertionId(Long.valueOf(animalItemDTO.getDesertionNo()));
        post.setImageUrl(animalItemDTO.getPopfile());
        post.setType(PostType.SYSTEM.getCode());
        post.setState(animalItemDTO.getProcessState());
        post.setGender(animalItemDTO.getSexCd());
        post.setNeuter(isEmpty(animalItemDTO.getNeuterYn()) ? NeuterType.U.name() : animalItemDTO.getNeuterYn());
        post.setUserId(PostType.SYSTEM.getCode());
        post.setUserName(animalItemDTO.getCareNm());
        post.setUserAddress(animalItemDTO.getCareAddr());
        post.setUserContact(animalItemDTO.getCareTel());
        post.setHappenDate(convertStringToDate(animalItemDTO.getHappenDt()));
        post.setHappenPlace(isNotEmpty(animalItemDTO.getOrgNm()) ? animalItemDTO.getOrgNm() : UNKNOWN);
        post.setUprCode(String.valueOf(shelter.getCityCode()));
        post.setOrgCode(String.valueOf(shelter.getTownCode()));
        post.setKindUpCode(String.valueOf(kind.getUpKindCode()));
        post.setKindCode(String.valueOf(kind.getKindCode()));
        try {
            post.setAge(Integer.valueOf(convertAge(animalItemDTO.getAge())));
        } catch (NumberFormatException nfe) {
            post.setAge(-1);
        }
        post.setWeight(Float.valueOf(convertWeight(animalItemDTO.getWeight())));
        post.setIntroduction(animalItemDTO.getNoticeComment());
        post.setFeature(animalItemDTO.getSpecialMark());
        post.setIsDisplay(TRUE);
        return post;
    }*/

    private PostDTO transform(Post post) {
        Breed breed = breedRepository.findByKindCode(post.getAnimalCode());
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
        postDTO.setKindUpCode(breed.getSpeciesCode());
        postDTO.setKindCode(breed.getKindCode());
        postDTO.setUserName(breed.getKindName());
        postDTO.setAge(post.getAge());
        postDTO.setWeight(post.getWeight());
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
