package com.papaolabs.openapi.domain.service;

import com.papaolabs.openapi.infrastructure.persistence.feign.vision.VisionApiClient;
import com.papaolabs.openapi.infrastructure.persistence.feign.vision.dto.VisionApiRequest;
import com.papaolabs.openapi.infrastructure.persistence.feign.vision.dto.VisionApiRequest.Request;
import com.papaolabs.openapi.infrastructure.persistence.feign.vision.dto.VisionApiRequest.Request.Feature;
import com.papaolabs.openapi.infrastructure.persistence.feign.vision.dto.VisionApiRequest.Request.Image;
import com.papaolabs.openapi.infrastructure.persistence.feign.vision.dto.VisionApiResponse.VisionResult;
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

@Service
public class VisionServiceImpl implements VisionService {
    public static final String LABEL_DETECTION = "LABEL_DETECTION";
    public static final String IMAGE_PROPERTIES = "IMAGE_PROPERTIES";
    public static final String SAFE_SEARCH_DETECTION = "SAFE_SEARCH_DETECTION";
    public static final List<String> filterNameList = Arrays.asList("dog",
                                                                    "dorgi",
                                                                    "paw",
                                                                    "fur",
                                                                    "snout",
                                                                    "puppy",
                                                                    "kennel",
                                                                    "carnivoran",
                                                                    "companion",
                                                                    "companion dog",
                                                                    "dog crate",
                                                                    "dog breed",
                                                                    "dog like mammal",
                                                                    "dog crossbreeds",
                                                                    "dog breed group",
                                                                    "cat like mammal",
                                                                    "mammal",
                                                                    "vertebrate",
                                                                    "animal shelter");
    @Value("${google.application.key}")
    private String visionAppKey;
    @NotNull
    private final VisionApiClient visionApiClient;

    public VisionServiceImpl(VisionApiClient visionApiClient) {
        this.visionApiClient = visionApiClient;
    }

    @Override
    public VisionResult getVisionResult(String imageUrl) {
        return visionApiClient.image(visionAppKey,
                                     createVisionRequests(Arrays.asList(imageUrl)))
                              .getResponses()
                              .stream()
                              .findFirst()
                              .get();
    }

    @Override
    public List<VisionResult> getVisionResult(List<String> imageUrls) {
        return visionApiClient.image(visionAppKey,
                                     createVisionRequests(imageUrls))
                              .getResponses();
    }

    private VisionApiRequest createVisionRequests(List<String> imageUrls) {
        List<Request> requests = new ArrayList<>();
        for (String imageUrl : imageUrls) {
            byte[] imageData = Base64.encodeBase64(getImageFromUrl(imageUrl));
            requests.add(createVisionRequest(new String(imageData)));
        }
        VisionApiRequest request = new VisionApiRequest();
        request.setRequests(requests);
        return request;
    }

    private Request createVisionRequest(String imageUrl) {
        Feature labelFeature = new Feature();
        labelFeature.setType(LABEL_DETECTION);
        Feature imageFeature = new Feature();
        imageFeature.setType(IMAGE_PROPERTIES);
        Feature safeSearchFeature = new Feature();
        safeSearchFeature.setType(SAFE_SEARCH_DETECTION);
        List<Feature> features = Arrays.asList(labelFeature, imageFeature, safeSearchFeature);
        Image image = new Image();
        image.setContent(imageUrl);
        Request request = new Request();
        request.setImage(image);
        request.setFeatures(features);
        return request;
    }

    private Boolean filterByLabel(String label) {
        return !filterNameList.stream()
                              .anyMatch(x -> x.equals(label));
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
