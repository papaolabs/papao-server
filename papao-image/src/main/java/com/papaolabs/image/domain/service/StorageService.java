package com.papaolabs.image.domain.service;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    List<PutObjectResult> upload(MultipartFile[] multipartFiles);

    byte[] download(String key);

    List<S3ObjectSummary> list();
}
