package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.Kind;
import com.papaolabs.api.domain.model.Post;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.KindRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.AnimalApiClient;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.AnimalApiResponse;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import com.papaolabs.api.interfaces.v1.dto.type.GenderType;
import com.papaolabs.api.interfaces.v1.dto.type.NeuterType;
import com.papaolabs.api.interfaces.v1.dto.type.PostType;
import com.papaolabs.api.interfaces.v1.dto.type.StateType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
    private static final String UNKNOWN = "UNKNOWN";
    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String MAX_SIZE = "100000";
    private static final String START_INDEX = "1";
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
    public PostDTO create(String imageUrl,
                          String type,
                          String gender,
                          String neuterYn,
                          String uid,
                          String contracts,
                          String happenDate,
                          String happenPlace,
                          String uprCode,
                          String orgCode,
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
        post.setNeuter(isEmpty(neuterYn) ? NeuterType.U.name() : neuterYn);
        post.setUid(uid);
        post.setContracts(contracts);
        post.setHappenDate(convertStringToDate(happenDate));
        post.setHappenPlace(isNotEmpty(happenPlace) ? happenPlace : UNKNOWN);
        post.setUprCode(uprCode);
        post.setOrgCode(orgCode);
        post.setKindUpCode(kindUpCode);
        post.setKindCode(isNotEmpty(kindCode) ? kindCode : StringUtils.EMPTY);
        post.setAge(isNotEmpty(age) ? Integer.valueOf(age) : -1);
        post.setWeight(isNotEmpty(weight) ? Float.valueOf(weight) : -1);
        post.setIntroduction(introduction);
        post.setFeature(feature);
        return transform(postRepository.save(post));
    }

    @Override
    public List<PostDTO> readPosts(String beginDate, String endDate, String kindUpCode, String uprCode, String orgCode) {
        if (isEmpty(beginDate)) {
            beginDate = getDefaultDate(DATE_FORMAT);
        }
        if (isEmpty(endDate)) {
            beginDate = getDefaultDate(DATE_FORMAT);
        }
        List<PostDTO> systemPosts = animalApiClient.animal(appKey, beginDate, endDate, kindUpCode, null,
                                                           uprCode, orgCode, null, null, START_INDEX, MAX_SIZE)
                                                   .getBody()
                                                   .getItems()
                                                   .getItem()
                                                   .stream()
                                                   .map(this::transform)
                                                   .collect(Collectors.toList());
        List<PostDTO> userPosts = postRepository.findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(convertStringToDate(beginDate),
                                                                                                            convertStringToDate(endDate))
                                                .stream()
                                                .filter(x -> isNotEmpty(kindUpCode) ? kindUpCode.equals(x.getKindUpCode()) : TRUE)
                                                .filter(x -> isNotEmpty(uprCode) ? uprCode.equals(x.getUprCode()) : TRUE)
                                                .filter(x -> isNotEmpty(orgCode) ? orgCode.equals(x.getOrgCode()) : TRUE)
                                                .map((this::transform))
                                                .collect(Collectors.toList());
        return Stream.of(systemPosts, userPosts)
                     .flatMap(Collection::stream)
                     .sorted(Comparator.comparing(PostDTO::getHappenDate))
                     .collect(Collectors.toList());
    }

    @Override
    public PostDTO readPost(String postId) {
        Post post = postRepository.findOne(Long.valueOf(postId));
        if (post == null) {
            log.debug("[NotFound] readPost - postId : {postId}", postId);
            return PostDTO.builder()
                          .id(-1L)
                          .build();
        }
        return transform(post);
    }

    @Override
    public void delete(String id) {
        postRepository.delete(Long.valueOf(id));
    }

    @Override
    public PostDTO setState(String postId, StateType state) {
        Post post = postRepository.findOne(Long.valueOf(postId));
        post.setState(state.name());
        return transform(post);
    }

    private PostDTO transform(Post post) {
        return PostDTO.builder()
                      .id(post.getId())
                      .type(post.getType())
                      .imageUrl(post.getImageUrl())
                      .kindUpCode(post.getKindUpCode())
                      .kindCode(convertKindName(post.getKindCode()))
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
        Kind kind = kindRepository.findByKindName(convertKindName(animalItemDTO.getKindCd()));
        return PostDTO.builder()
                      .id(Long.valueOf(animalItemDTO.getDesertionNo()))
                      .type(PostType.SYSTEM.getCode())
                      .imageUrl(animalItemDTO.getPopfile())
                      .kindUpCode(kind != null ? String.valueOf(kind.getUpKindCode()) : StringUtils.EMPTY)
                      .kindCode(kind != null ? kind.getKindName() : convertKindName(animalItemDTO.getKindCd()))
                      .happenDate(animalItemDTO.getHappenDt())
                      .happenPlace(animalItemDTO.getOrgNm())
                      .contracts(animalItemDTO.getCareTel())
                      .weight(animalItemDTO.getWeight())
                      .gender(animalItemDTO.getSexCd())
                      .state(animalItemDTO.getProcessState())
                      .neuter(animalItemDTO.getNeuterYn())
                      .feature(animalItemDTO.getSpecialMark())
                      .introduction(animalItemDTO.getNoticeComment())
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
}
