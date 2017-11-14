package com.papaolabs.image.interfaces.v1;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.papaolabs.image.domain.service.StorageService;
import com.papaolabs.image.domain.service.StorageServiceImpl;
import com.papaolabs.image.domain.service.VisionService;
import com.papaolabs.image.infrastructure.dto.UploadResult;
import com.papaolabs.image.infrastructure.feign.vision.dto.VisionApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class V1ImageController {
    @NotNull
    private final StorageService storageService;
    @NotNull
    private final VisionService visionService;

    @Value("${hostname}")
    private String hostname;

    public V1ImageController(StorageServiceImpl storageService, VisionService visionService) {
        this.storageService = storageService;
        this.visionService = visionService;
    }

    @PostMapping(value = "/upload")
    public List<UploadResult> upload(@RequestParam("file") MultipartFile[] multipartFiles) throws JsonProcessingException {
        List<UploadResult> uploadResult = storageService.upload(multipartFiles);
        VisionApiResponse.VisionResult visionResult = visionService.getVisionResult(hostname+"/download/"+uploadResult.get(0).getImageUrl());
        return uploadResult;
    }

    @GetMapping(value = "/download/{path:.*}")
    public ResponseEntity<byte[]> download(@PathVariable("path") String path) throws IOException {
        return storageService.download(path);
    }

    @GetMapping(value = "/list")
    public List<S3ObjectSummary> list() throws IOException {
        return storageService.list();
    }

    @GetMapping(value = "/vision")
    public VisionApiResponse.VisionResult getVisionResult(@RequestParam String imageUrl) {
        return visionService.getVisionResult(imageUrl);
    }

    @GetMapping("/vision/list")
    public List<VisionApiResponse.VisionResult> getVisionResults(@RequestParam List<String> imageUrl) {
        return visionService.getVisionResult(imageUrl);
    }
}
