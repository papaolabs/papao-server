package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.dto.PostDTO;

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
                String weight,
                String introduction,
                String feature
    );

    List<PostDTO> readPosts(String beginDate, String endDate, String upKindCode, String uprCode, String orgCode);

    PostDTO readPost(String postId);

    void delete(String id);
}
