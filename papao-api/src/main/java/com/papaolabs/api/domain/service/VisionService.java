package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.dto.PostDTO;

public interface VisionService {
    void syncVisionData(PostDTO post);
}
