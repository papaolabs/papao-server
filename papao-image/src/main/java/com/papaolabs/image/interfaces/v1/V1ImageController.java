package com.papaolabs.image.interfaces.v1;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.papaolabs.image.domain.service.StorageService;
import com.papaolabs.image.domain.service.StorageServiceImpl;
import com.papaolabs.image.domain.service.VisionService;
import com.papaolabs.image.infrastructure.feign.vision.dto.VisionApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    public V1ImageController(StorageServiceImpl storageService, VisionService visionService) {
        this.storageService = storageService;
        this.visionService = visionService;
    }

    @PostMapping(value = "/upload")
    public List<PutObjectResult> upload(@RequestParam("file") MultipartFile[] multipartFiles) {
        return storageService.upload(multipartFiles);
    }

    @GetMapping(value = "/download")
    public ResponseEntity<byte[]> download(@RequestParam String key) throws IOException {
        return storageService.download(key);
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
