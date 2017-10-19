package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.VisionColor;
import com.papaolabs.api.domain.model.VisionLabel;
import com.papaolabs.api.domain.model.VisionType;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.VisionColorRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.VisionLabelRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.VisionTypeRepository;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.VisionApiClient;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiRequest;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiRequest.Request;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiRequest.Request.Feature;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiResponse.VisionResult;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiResponse.VisionResult.Label;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiResponse.VisionResult.Type;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.VisionApiResponse.VisionResult.VisionProperties.DominantColor
    .Properties;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisionServiceImpl implements VisionService {
    public static final List<String> filterNameList = Arrays.asList("dog",
                                                                    "dorgi",
                                                                    "paw",
                                                                    "fur",
                                                                    "snout",
                                                                    "dog breed",
                                                                    "dog like mammal",
                                                                    "dog breed group",
                                                                    "cat like mammal",
                                                                    "mammal",
                                                                    "vertebrate");
    @Value("${google.application.key}")
    private String visionAppKey;
    @NotNull
    private final VisionApiClient visionApiClient;
    @NotNull
    private final VisionLabelRepository labelRepository;
    @NotNull
    private final VisionColorRepository colorRepository;
    @NotNull
    private final VisionTypeRepository typeRepository;

    public VisionServiceImpl(VisionApiClient visionApiClient, VisionLabelRepository labelRepository,
                             VisionColorRepository colorRepository,
                             VisionTypeRepository typeRepository) {
        this.visionApiClient = visionApiClient;
        this.labelRepository = labelRepository;
        this.colorRepository = colorRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public void syncVisionData(PostDTO post) {
        VisionApiResponse result = visionApiClient.image(visionAppKey,
                                                         createVisionApiRequest(Arrays.asList(post)));
        List<VisionResult> visionResults = result.getResponses();
        for (VisionResult visionResult : visionResults) {
            labelRepository.save(visionResult.getLabelAnnotations()
                                             .stream()
                                             .filter(x -> filterLabelName(x.getDescription()))
                                             .map(this::transform)
                                             .map(x -> {
                                                 x.setPostId(post.getId());
                                                 return x;
                                             })
                                             .collect(Collectors.toList()));
            VisionType visionType = transform(visionResult.getSafeSearchAnnotation());
            visionType.setPostId(post.getId());
            typeRepository.save(visionType);
            colorRepository.save(visionResult.getImagePropertiesAnnotation()
                                             .getDominantColors()
                                             .getColors()
                                             .stream()
                                             .map(this::transform)
                                             .map(x -> {
                                                 x.setPostId(post.getId());
                                                 return x;
                                             })
                                             .collect(Collectors.toList()));
        }
    }

    @Override
    public void syncVisionData(List<PostDTO> posts) {
        VisionApiResponse result = visionApiClient.image(visionAppKey,
                                                         createVisionApiRequest(posts));
        List<VisionResult> visionResults = result.getResponses();
        for (VisionResult visionResult : visionResults) {
            List<VisionLabel> visionLabels = visionResult.getLabelAnnotations()
                                                         .stream()
                                                         .filter(x -> filterLabelName(x.getDescription()))
                                                         .map(this::transform)
                                                         .collect(Collectors.toList());
            VisionType visionType = transform(visionResult.getSafeSearchAnnotation());
            List<VisionColor> visionColors = visionResult.getImagePropertiesAnnotation()
                                                         .getDominantColors()
                                                         .getColors()
                                                         .stream()
                                                         .map(this::transform)
                                                         .collect(Collectors.toList());
            for (int i = 0; i < posts.size(); i++) {
                Long postId = posts.get(i)
                                   .getId();
                String imageUrl = posts.get(i)
                                       .getImageUrl(); // Todo : vision api 테스트 동안에만 저장하도록 함
                visionLabels.forEach(x -> {
                    x.setPostId(postId);
                    x.setImageUrl(imageUrl);
                });
                visionColors.forEach(x -> {
                    x.setPostId(postId);
                    x.setImageUrl(imageUrl);
                });
                visionType.setPostId(postId);
                visionType.setImageUrl(imageUrl);
            }
            labelRepository.save(visionLabels);
            typeRepository.save(visionType);
            colorRepository.save(visionColors);
        }
    }

    private Boolean filterLabelName(String name) {
        return !filterNameList.stream()
                              .anyMatch(x -> x.equals(name));
    }

    private VisionLabel transform(Label label) {
        VisionLabel visionLabel = new VisionLabel();
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

    private Request createRequest(String imageUrl) {
        List<Feature> features = Arrays.asList(Feature.builder()
                                                      .type("LABEL_DETECTION")
                                                      .build(),
                                               Feature.builder()
                                                      .type("IMAGE_PROPERTIES")
                                                      .build(),
                                               Feature.builder()
                                                      .type("SAFE_SEARCH_DETECTION")
                                                      .build());
        return Request.builder()
                      .image(Request.Image.builder()
                                          .content(imageUrl)
                                          .build())
                      .features(features)
                      .build();
    }

    private VisionApiRequest createVisionApiRequest(List<PostDTO> posts) {
        List<Request> requests = new ArrayList<>();
        for (PostDTO post : posts) {
            byte[] imageData = Base64.encodeBase64(getImageFromUrl(post.getImageUrl()));
            requests.add(createRequest(new String(imageData)));
        }
        return VisionApiRequest.builder()
                               .requests(requests)
                               .build();
    }

    private byte[] getImageFromUrl(String imageUrl) {
        URL url = null;
        try {
            url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int n = 0;
            byte[] buffer = new byte[1024];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }
}
