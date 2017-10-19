package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiResponse;

public interface VisionService {
    void create(VisionApiResponse response);
}
