package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.VisionColor;
import com.papaolabs.api.domain.model.VisionLabel;
import com.papaolabs.api.domain.model.VisionType;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.VisionColorRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.VisionLabelRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.VisionTypeRepository;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiResponse.VisionResult;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiResponse.VisionResult.Label;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiResponse.VisionResult.Type;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiResponse.VisionResult.VisionProperties.DominantColor
    .Properties;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisionServiceImpl implements VisionService {
    public static final List<String> filterNameList = Arrays.asList("dog",
                                                                    "dog breed",
                                                                    "dog like mammal",
                                                                    "dog breed group",
                                                                    "mammal",
                                                                    "vertebrate");
    @NotNull
    private final VisionLabelRepository labelRepository;
    @NotNull
    private final VisionColorRepository colorRepository;
    @NotNull
    private final VisionTypeRepository typeRepository;

    public VisionServiceImpl(VisionLabelRepository labelRepository,
                             VisionColorRepository colorRepository,
                             VisionTypeRepository typeRepository) {
        this.labelRepository = labelRepository;
        this.colorRepository = colorRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public void create(VisionApiResponse response) {
        List<VisionResult> visionResults = response.getResponses();
        for (VisionResult visionResult : visionResults) {
            labelRepository.save(visionResult.getLabelAnnotations()
                                             .stream()
                                             .filter(x -> filterLabelName(x.getDescription()))
                                             .map(this::transform)
                                             .collect(Collectors.toList()));
            typeRepository.save(transform(visionResult.getSafeSearchAnnotation()));
            colorRepository.save(visionResult.getImagePropertiesAnnotation()
                                             .getDominantColors()
                                             .getColors()
                                             .stream()
                                             .map(this::transform)
                                             .collect(Collectors.toList()));
        }
    }

    private Boolean filterLabelName(String name) {
        return !filterNameList.stream()
                             .anyMatch(x -> x.equals(name));
    }

    private VisionLabel transform(Label label) {
        VisionLabel visionLabel = new VisionLabel();
        visionLabel.setPid("-1");
        visionLabel.setMid(label.getMid());
        visionLabel.setName(label.getDescription());
        visionLabel.setScore(label.getScore());
        return visionLabel;
    }

    private VisionColor transform(Properties color) {
        VisionColor visionColor = new VisionColor();
        visionColor.setRed(color.getColor()
                                .getRed());
        visionColor.setGreen(color.getColor()
                                  .getGreen());
        visionColor.setBlue(color.getColor()
                                 .getBlue());
        visionColor.setScore(color.getScore());
        visionColor.setPixelFraction(color.getPixelFraction());
        return visionColor;
    }

    private VisionType transform(Type type) {
        VisionType visionType = new VisionType();
        visionType.setAdult(type.getAdult());
        visionType.setSpoof(type.getSpoof());
        visionType.setMedical(type.getMedical());
        visionType.setViolence(type.getViolence());
        return visionType;
    }
}
