package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.Post;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.KindRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import com.papaolabs.api.interfaces.v1.dto.type.GenderType;
import com.papaolabs.api.interfaces.v1.dto.type.NeuterType;
import com.papaolabs.api.interfaces.v1.dto.type.StateType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
    private static final String UNKNOWN = "UNKNOWN";
    private static final String DATE_FORMAT = "yyyyMMdd";
    @NotNull
    private final PostRepository postRepository;
    @NotNull
    private final KindRepository kindRepository;

    public PostServiceImpl(PostRepository postRepository, KindRepository kindRepository) {
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
                          Float weight,
                          String introduction,
                          String feature) {
        Post post = new Post();
        post.setImageUrl(imageUrl);
        post.setType(type);
        post.setState(StateType.PROCESS.name());
        post.setGender(isEmpty(gender) ? GenderType.Q.name() : gender);
        post.setNeuter(isEmpty(neuterYn) ? NeuterType.U.name() : neuterYn);
        post.setUserId(uid);
        post.setUserContracts(contracts);
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
        post.setIsDisplay(TRUE);
        return transform(postRepository.save(post));
    }

    @Override
    public List<PostDTO> readPosts(String beginDate, String endDate, String kindUpCode, String uprCode, String orgCode) {
        return postRepository.findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(convertStringToDate(beginDate),
                                                                                         convertStringToDate(endDate))
                             .stream()
                             .filter(Post::getIsDisplay)
                             .filter(x -> isNotEmpty(kindUpCode) ? kindUpCode.equals(x.getKindUpCode()) : TRUE)
                             .filter(x -> isNotEmpty(uprCode) ? uprCode.equals(x.getUprCode()) : TRUE)
                             .filter(x -> isNotEmpty(orgCode) ? orgCode.equals(x.getOrgCode()) : TRUE)
                             .map((this::transform))
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
    public PostDTO delete(String postId) {
        Post post = postRepository.findOne(Long.valueOf(postId));
        if (post == null) {
            log.debug("[NotFound] delete - postId : {postId}", postId);
            return PostDTO.builder()
                          .id(-1L)
                          .build();
        } else if (!post.getIsDisplay()) {
            log.debug("[NotValid] delete - isDisplay : {isDisplay}, postId : {postId}", post.getIsDisplay(), postId);
            return PostDTO.builder()
                          .id(-1L)
                          .build();
        }
        post.setIsDisplay(FALSE);
        postRepository.save(post);
        return transform(post);
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
                      .desertionId(post.getDesertionId())
                      .type(post.getType())
                      .imageUrl(post.getImageUrl())
                      .kindUpCode(post.getKindUpCode())
                      .kindCode(convertKindName(post.getKindCode()))
                      .kindName(kindRepository.findByKindCode(Long.valueOf(post.getKindCode()))
                                              .getKindName())
                      .happenDate(convertDateToString(post.getHappenDate()))
                      .happenPlace(post.getHappenPlace())

                      .userId(post.getUserId())
                      .userName(post.getUserName())
                      .userAddress(post.getUserAddress())
                      .userContracts(post.getUserContracts())

                      .weight(String.valueOf(post.getWeight()))
                      .gender(post.getGender())
                      .state(post.getState())
                      .neuter(post.getNeuter())
                      .feature(post.getFeature())
                      .introduction(isNotEmpty(post.getIntroduction()) ? post.getIntroduction() : StringUtils.EMPTY)
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
