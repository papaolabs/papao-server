package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Breed;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Comment;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Image;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.QPost;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Region;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Shelter;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BreedRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.CommentRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.RegionRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.ShelterRepository;
import com.papaolabs.api.interfaces.v1.controller.response.PostDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PostPreviewDTO;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Value("${seoul.api.animal.appKey}")
    private String appKey;
    private static final String DATE_FORMAT = "yyyyMMdd";
    @NotNull
    private final PostRepository postRepository;
    @NotNull
    private final RegionRepository regionRepository;
    @NotNull
    private final BreedRepository breedRepository;
    @NotNull
    private final ShelterRepository shelterRepository;
    @NotNull
    private final CommentRepository commentRepository;
    @NotNull
    private final BookmarkService bookmarkService;

    public PostServiceImpl(PostRepository postRepository,
                           RegionRepository regionRepository,
                           BreedRepository breedRepository,
                           ShelterRepository shelterRepository,
                           CommentRepository commentRepository,
                           BookmarkService bookmarkService) {
        this.postRepository = postRepository;
        this.regionRepository = regionRepository;
        this.breedRepository = breedRepository;
        this.shelterRepository = shelterRepository;
        this.commentRepository = commentRepository;
        this.bookmarkService = bookmarkService;
    }

    @Override
    public PostDTO create(String happenDate,
                          String happenPlace,
                          String uid,
                          String postType,
                          String stateType,
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
        Breed breed = breedRepository.findByKindCode(Long.valueOf(kindCode));
        post.setUpKindCode(breed.getUpKindCode());
        post.setKindCode(breed.getKindCode());
        post.setKindName(breed.getKindName());
        post.setHelperContact(contact);
        post.setGenderType(Post.GenderType.getType(gender));
        post.setNeuterType(Post.NeuterType.getType(neuter));
        post.setStateType(Post.StateType.getType(stateType));
        post.setAge(age);
        post.setWeight(weight);
        post.setFeature(feature);
        Region region = regionRepository.findBySidoCodeAndGunguCode(sidoCode, gunguCode);
        post.setHappenSidoCode(region.getSidoCode());
        post.setHappenGunguCode(region.getGunguCode());
        Shelter shelter = shelterRepository.findByShelterCode(-1L);
        post.setShelterCode(shelter.getShelterCode());
        post.setShelterName(shelter.getShelterName());
        post.setShelterContact(contact);
        post.setDisplay(TRUE);
        return transform(postRepository.save(post));
    }

    @Override
    public List<PostPreviewDTO> readPosts(List<String> postType,
                                          String beginDate,
                                          String endDate,
                                          String upKindCode,
                                          String kindCode,
                                          String uprCode,
                                          String orgCode,
                                          String genderType,
                                          String neuterType) {
        if (isEmpty(beginDate)) {
            beginDate = getDefaultDate(DATE_FORMAT);
        }
        if (isEmpty(endDate)) {
            endDate = getDefaultDate(DATE_FORMAT);
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Iterable<Post> results = postRepository.findAll(generateQuery(postType,
                                                                      beginDate,
                                                                      endDate,
                                                                      upKindCode,
                                                                      kindCode,
                                                                      uprCode,
                                                                      orgCode, genderType, neuterType));
        stopWatch.stop();
        log.debug("originalPosts get time :: {} ", stopWatch.getLastTaskTimeMillis());
        return StreamSupport.stream(results.spliterator(), false)
                            .filter(Post::getDisplay)
                            .map(this::previewTransform)
                            .sorted(Comparator.comparing(PostPreviewDTO::getHappenDate))
                            .collect(Collectors.toList());
    }

    @Override
    public List<PostPreviewDTO> readPostsByPage(List<String> postType,
                                                String beginDate,
                                                String endDate,
                                                String upKindCode,
                                                String kindCode,
                                                String uprCode,
                                                String orgCode,
                                                String genderType,
                                                String neuterType,
                                                String page,
                                                String size) {
        if (isEmpty(beginDate)) {
            beginDate = getDefaultDate(DATE_FORMAT);
        }
        if (isEmpty(endDate)) {
            endDate = getDefaultDate(DATE_FORMAT);
        }
        PageRequest pageRequest = new PageRequest(Integer.valueOf(page), Integer.valueOf(size));
        Page<Post> results = postRepository.findAll(generateQuery(postType,
                                                                  beginDate,
                                                                  endDate,
                                                                  upKindCode,
                                                                  kindCode,
                                                                  uprCode,
                                                                  orgCode,
                                                                  genderType,
                                                                  neuterType),
                                                    pageRequest);
        return results.getContent()
                      .stream()
                      .map((this::previewTransform))
                      .sorted(Comparator.comparing(PostPreviewDTO::getHappenDate))
                      .collect(Collectors.toList());
    }

    private BooleanBuilder generateQuery(List<String> postType,
                                         String beginDate,
                                         String endDate,
                                         String upKindCode,
                                         String kindCode,
                                         String uprCode,
                                         String orgCode,
                                         String genderType,
                                         String neuterType) {
        QPost post = QPost.post;
        BooleanBuilder builder = new BooleanBuilder().and(post.happenDate.between(convertStringToDate(beginDate),
                                                                                  convertStringToDate(endDate))
                                                                         .and(post.isDisplay.eq(TRUE)));
        if (postType != null) {
            if (postType.size() > 0) {
                builder.and(post.postType.eq(Post.PostType.getType(postType.get(0))));
                if (postType.size() > 1) {
                    for (int i = 1; i < postType.size(); i++) {
                        builder.or(post.postType.eq(Post.PostType.getType(postType.get(i))));
                    }
                }
            }
        }
        if (isNotEmpty(uprCode)) {
            builder.and(post.happenSidoCode.eq(Long.valueOf(uprCode)));
        }
        if (isNotEmpty(orgCode)) {
            builder.and(post.happenGunguCode.eq(Long.valueOf(orgCode)));
        }
        if (isNotEmpty(upKindCode)) {
            builder.and(post.upKindCode.eq(Long.valueOf(upKindCode)));
        }
        if (isNotEmpty(kindCode)) {
            builder.and(post.kindCode.eq(Long.valueOf(kindCode)));
        }
        if (isNotEmpty(genderType)) {
            builder.and(post.genderType.eq(Post.GenderType.getType(genderType)));
        }
        if (isNotEmpty(neuterType)) {
            builder.and(post.neuterType.eq(Post.NeuterType.getType(neuterType)));
        }
        return builder;
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
    public PostDTO delete(String postId, String userId) {
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

    private PostPreviewDTO previewTransform(Post post) {
        PostPreviewDTO postPreviewDTO = new PostPreviewDTO();
        postPreviewDTO.setId(post.getId());
        postPreviewDTO.setPostType(post.getPostType());
        postPreviewDTO.setStateType(post.getStateType());
        Image image = post.getImages()
                          .stream()
                          .findFirst()
                          .get();
        PostPreviewDTO.ImageUrl imageUrl = new PostPreviewDTO.ImageUrl();
        imageUrl.setKey(image.getId());
        imageUrl.setUrl(image.getUrl());
        postPreviewDTO.setImageUrls(Arrays.asList(imageUrl));
        postPreviewDTO.setGenderType(post.getGenderType());
        postPreviewDTO.setHappenDate(convertDateToString(post.getHappenDate()));
        postPreviewDTO.setHitCount(post.getHitCount());
        postPreviewDTO.setCreatedDate(post.getCreatedDateTime()
                                          .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        postPreviewDTO.setUpdatedDate(post.getLastModifiedDateTime()
                                          .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // Comment 세팅
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        postPreviewDTO.setCommentCount(Long.valueOf(comments.size()));
        // Breed 세팅
        Breed breed = breedRepository.findByKindCode(post.getKindCode());
        postPreviewDTO.setKindName(breed.getKindName());
        // Region/Shelter 세팅
        Shelter shelter = shelterRepository.findByShelterCode(post.getShelterCode());
        postPreviewDTO.setHappenPlace(StringUtils.join(shelter.getSidoName(), SPACE, shelter.getGunguName()));
        return postPreviewDTO;
    }

    private PostDTO transform(Post post) {
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
        postDTO.setHappenDate(convertDateToString(post.getHappenDate()));
        postDTO.setHappenPlace(post.getHappenPlace());
        postDTO.setManagerName(post.getHelperName());
        postDTO.setManagerContact(post.getHelperContact());
        postDTO.setAge(post.getAge());
        postDTO.setWeight(post.getWeight());
        postDTO.setHitCount(post.getHitCount());
        postDTO.setCreatedDate(post.getCreatedDateTime()
                                   .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        postDTO.setUpdatedDate(post.getLastModifiedDateTime()
                                   .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // Breed 세팅
        Breed breed = breedRepository.findByKindCode(post.getKindCode());
        // Region/Shelter 세팅
        Region region = regionRepository.findBySidoCodeAndGunguCode(post.getHappenSidoCode(), post.getHappenGunguCode());
        Shelter shelter = shelterRepository.findByShelterCode(post.getShelterCode());
        // Todo User 세팅
        postDTO.setUpKindName(breed.getUpKindName());
        postDTO.setKindName(breed.getKindName());
        postDTO.setSidoName(region.getSidoName());
        postDTO.setGunguName(region.getGunguName());
        postDTO.setShelterName(shelter.getShelterName());
        postDTO.setBookmarkCount(bookmarkService.countBookmark(String.valueOf(post.getId())));
        return postDTO;
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
