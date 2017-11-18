package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.interfaces.v1.controller.response.PostDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PostPreviewDTO;

import java.util.List;

public interface PostService {
    PostDTO create(String happenDate,
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
                   Long gunguCode
    );

    List<PostPreviewDTO> readPosts(List<String> postType,
                                   String beginDate,
                                   String endDate,
                                   String upKindCode,
                                   String kindCode,
                                   String uprCode,
                                   String orgCode,
                                   String genderType,
                                   String neuterType);

    List<PostPreviewDTO> readPostsByPage(List<String> postType,
                                         String beginDate,
                                         String endDate,
                                         String upKindCode,
                                         String kindCode,
                                         String uprCode,
                                         String orgCode,
                                         String genderType,
                                         String neuterType,
                                         String page,
                                         String size);

    PostDTO readPost(String postId);

    PostDTO delete(String postId, String userId);

    PostDTO setState(String postId, Post.StateType state);

/*    void syncPosts(String beginDate, String endDate);*/
}
