package com.papaolabs.image.domain.service;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.papaolabs.image.infrastructure.dto.UploadResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    List<UploadResult> upload(MultipartFile[] multipartFiles) throws JsonProcessingException;

    ResponseEntity<byte[]> download(String key);

    List<S3ObjectSummary> list();
}
