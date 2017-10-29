package com.papaolabs.openapi.domain.service;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class CaptureServiceImpl implements CaptureService {
    public static final String DEFAULT_BREED_TB_NAME = "BREED_INFO_TB";
    public static final String DEFAULT_REGION_TB_NAME = "REGION_INFO_TB";
    public static final String DEFAULT_SHELTER_TB_NAME = "SHELTER_INFO_TB";
    @NotNull
    private final OperationService operationService;

    public CaptureServiceImpl(OperationService operationService) {
        this.operationService = operationService;
    }

    @Override
    public String captureBreedList(String tableName) {
        String tbName = isNotEmpty(tableName) ? tableName : DEFAULT_BREED_TB_NAME;
        return this.operationService.getBreedList()
                                    .stream()
                                    .map(x -> "INSERT INTO " + tbName
                                        + " (CATEGORY_CODE, CODE, NAME, CREATED_DATE, UPDATED_DATE) VALUES" +
                                        " (" + x.getCategory()
                                                .getCode() + ", " + x.getCode() + ", '" + x.getName() + "', CURRENT_TIMESTAMP, " +
                                        "CURRENT_TIMESTAMP);\n"
                                    )
                                    .collect(Collectors.joining());
    }

    @Override
    public String captureShelterList(String tableName) {
        String tbName = isNotEmpty(tableName) ? tableName : DEFAULT_SHELTER_TB_NAME;
        return this.operationService.getShelterList()
                                    .stream()
                                    .map(x -> "INSERT INTO " + tbName + " (CITY_CODE, CITY_NAME, TOWN_CODE, TOWN_NAME, SHELTER_CODE, " +
                                        "SHELTER_NAME, CREATED_DATE, UPDATED_DATE) VALUES (" + x.getRegion()
                                                                                                .getCityCode() + ", '" + x.getRegion()
                                                                                                                          .getCityName()
                                        + "', " + x.getRegion()
                                                   .getTownCode() + ", " +
                                        "'" + x.getRegion()
                                               .getTownName() + "', " +
                                        "" + x.getCode() + ", '" + x.getName() + "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);\n")
                                    .collect(Collectors.joining());
    }
}
