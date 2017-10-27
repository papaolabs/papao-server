package com.papaolabs.openapi.interfaces.v1;

import com.papaolabs.openapi.domain.service.VisionService;
import com.papaolabs.openapi.infrastructure.persistence.feign.vision.dto.VisionApiResponse.VisionResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/openapi/v1/vision")
public class V1VisionController {
    @NotNull
    private final VisionService visionService;

    public V1VisionController(VisionService visionService) {
        this.visionService = visionService;
    }

    @GetMapping
    public VisionResult getVisionResult(@RequestParam String imageUrl) {
        return visionService.getVisionResult(imageUrl);
    }

    @GetMapping("/list")
    public List<VisionResult> getVisionResults(@RequestParam List<String> imageUrl) {
        return visionService.getVisionResult(imageUrl);
    }
}
