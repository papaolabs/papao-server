package com.papaolabs.openapi.domain.service;

import java.util.List;

public interface VisionDataService {
    void syncVisionData(PostDTO post);
    void syncVisionData(List<PostDTO> posts);
}
