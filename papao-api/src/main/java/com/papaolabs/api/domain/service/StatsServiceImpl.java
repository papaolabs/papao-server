package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.restapi.feign.AnimalApiClient;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.AnimalApiResponse;
import com.papaolabs.api.interfaces.v1.dto.StatsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class StatsServiceImpl implements StatsService {
    @Value("${seoul.api.animal.appKey}")
    private String appKey;
    private static final String ADOPTION_STATE = "종료(입양)";
    private static final String RETURN_STATE = "종료(반환)";
    private static final String EUTHANASIA_STATE = "종료(안락사)";
    @NotNull
    private final AnimalApiClient animalApiClient;

    public StatsServiceImpl(AnimalApiClient animalApiClient) {
        this.animalApiClient = animalApiClient;
    }

    @Override
    public StatsDTO getStats(String beginDate, String endDate) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        if(isEmpty(beginDate)) {
            beginDate = now.format(formatter);
        }
        if(isEmpty(endDate)) {
            endDate = now.format(formatter);
        }
        AnimalApiResponse response = animalApiClient.animal(appKey, beginDate, endDate, null, null, null, null, null, null, "1", "100000");
        List<AnimalApiResponse.Body.Items.AnimalItemDTO> items = response.getBody()
                                                                         .getItems()
                                                                         .getItem();
        StatsDTO stats = new StatsDTO();
        stats.setAdoptionRate((items.stream()
                                    .filter(x -> ADOPTION_STATE.equals(x.getProcessState()))
                                    .count() / Double.valueOf(items.size())));
        stats.setEuthanasiaRate((items.stream()
                                      .filter(x -> EUTHANASIA_STATE.equals(x.getProcessState()))
                                      .count() / Double.valueOf(items.size())));
        stats.setReturnCount(items.stream()
                                  .filter(x -> RETURN_STATE.equals(x.getProcessState()))
                                  .count());
        stats.setSaveCount(Long.valueOf(items.size()));
        return stats;
    }
}
