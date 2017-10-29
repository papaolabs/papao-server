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
                                    .map(x -> "insert into " + tbName
                                        + " (species_code, code, name, created_date, updated_date) VALUES" +
                                        " (" + x.getSpecies()
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
                                    .map(x -> "insert into " + tbName + " (sido_code, sido_name, gungu_code, gungu_name, shelter_code, " +
                                        "shelter_name, created_date, updated_date) VALUES (" + x.getRegion()
                                                                                                .getSidoCode() + ", '" + x.getRegion()
                                                                                                                          .getSidoName()
                                        + "', " + x.getRegion()
                                                   .getGunguCode() + ", " +
                                        "'" + x.getRegion()
                                               .getGunguName() + "', " +
                                        "" + x.getCode() + ", '" + x.getName() + "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);\n")
                                    .collect(Collectors.joining());
    }
}
