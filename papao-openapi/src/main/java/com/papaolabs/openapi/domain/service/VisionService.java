package com.papaolabs.openapi.domain.service;

import com.papaolabs.client.vision.dto.VisionApiResponse.VisionResult;

import java.util.List;

public interface VisionService {
    VisionResult getVisionResult(String imageUrl);
    List<VisionResult> getVisionResult(List<String> imageUrl);
}
