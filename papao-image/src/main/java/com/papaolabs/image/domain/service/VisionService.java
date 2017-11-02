package com.papaolabs.image.domain.service;

import com.papaolabs.image.infrastructure.feign.vision.dto.VisionApiResponse.VisionResult;

import java.util.List;

public interface VisionService {
    VisionResult getVisionResult(String imageUrl);
    List<VisionResult> getVisionResult(List<String> imageUrl);
}
