package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import com.papaolabs.api.interfaces.v1.dto.type.StateType;

import java.util.List;

public interface PostService {
    PostDTO create(String imageUrl,
                   String state,
                   String gender,
                   String neuterYn,
                   String managerName,
                   String managerTel,
                   String happenDate,
                   String happenPlace,
                   String uprCode,
                   String orgCode,
                   String kindUpCode,
                   String kindCode,
                   String age,
                   Float weight,
                   String introduction,
                   String feature
    );

    List<PostDTO> readPosts(String beginDate,
                            String endDate,
                            String upKindCode,
                            String uprCode,
                            String orgCode,
                            Integer page,
                            Integer size);

    PostDTO readPost(String postId);

    PostDTO delete(String id);

    PostDTO setState(String postId, StateType state);
}
