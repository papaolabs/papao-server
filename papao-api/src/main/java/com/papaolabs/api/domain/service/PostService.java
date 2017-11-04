package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import com.papaolabs.api.interfaces.v1.dto.type.StateType;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PostService {
    PostDTO create(String happenDate,
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
                   String orgCode
    );

    List<PostDTO> readPosts(String beginDate,
                            String endDate,
                            String upKindCode,
                            String uprCode,
                            String orgCode);

    List<PostDTO> readPostsByPage(String beginDate,
                                  String endDate,
                                  String upKindCode,
                                  String uprCode,
                                  String orgCode,
                                  String page,
                                  String size);

    PostDTO readPost(String postId);

    PostDTO delete(String id);

    PostDTO setState(String postId, StateType state);

/*    void syncPosts(String beginDate, String endDate);*/
}
