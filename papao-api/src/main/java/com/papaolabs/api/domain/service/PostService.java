package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.interfaces.v1.controller.response.PostDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PostPreviewDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PostRankingDTO;
import com.papaolabs.api.interfaces.v1.controller.response.ResponseType;

import java.util.List;

public interface PostService {
    ResponseType create(String happenDate,
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

/*    List<PostPreviewDTO> readPosts(List<String> postType,
                                   String beginDate,
                                   String endDate,
                                   String upKindCode,
                                   String kindCode,
                                   String uprCode,
                                   String orgCode,
                                   String genderType,
                                   String neuterType);*/

    PostPreviewDTO readPostsByPage(List<String> postType,
                                   String userId,
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

    ResponseType delete(String postId, String userId);

    ResponseType setState(String postId, String userId, Post.StateType state);

    PostRankingDTO readPostRanking(String beginDate, String endDate);

    PostPreviewDTO readBookmarkByUserId(String userId, String index, String size);
/*    void syncPosts(String beginDate, String endDate);*/
}
