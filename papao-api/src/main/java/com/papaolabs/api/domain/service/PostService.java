package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.Post;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;

import java.util.List;

public interface PostService {
    Post create(String imageUrl,
                String state,
                String gender,
                String neuterYn,
                String description,
                String managerName,
                String managerTel,
                String happenDate,
                String happenPlace,
                String kindCode,
                String age,
                String weight
    );

    void delete(String id);

    List<PostDTO> getAnimalList(String beginDate,
                                String endDate,
                                String upKindCode,
                                String kindCode,
                                String uprCode,
                                String orgCode,
                                String shelterCode,
                                String state,
                                String pageNo,
                                String numOfRows);

    List<PostDTO> readPosts(String beginDate, String endDate, String upKindCode, String uprCode, String orgCode);
}
