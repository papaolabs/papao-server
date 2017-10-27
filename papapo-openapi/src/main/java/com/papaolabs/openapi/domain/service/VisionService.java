package com.papaolabs.openapi.domain.service;

import com.papaolabs.openapi.infrastructure.persistence.feign.vision.dto.VisionApiResponse.VisionResult;

import java.util.List;

public interface VisionService {
    VisionResult getVisionResult(String imageUrl);
    List<VisionResult> getVisionResult(List<String> imageUrl);
}
