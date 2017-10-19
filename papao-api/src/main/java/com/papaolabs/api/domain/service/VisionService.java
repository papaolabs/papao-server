package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.dto.PostDTO;

import java.util.List;

public interface VisionService {
    void syncVisionData(PostDTO post);
    void syncVisionData(List<PostDTO> posts);
}
