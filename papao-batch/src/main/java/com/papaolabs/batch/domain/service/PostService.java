package com.papaolabs.batch.domain.service;

import com.papaolabs.batch.infrastructure.jpa.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> syncPostList(String beginDate, String endDate);
}