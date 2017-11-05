package com.papaolabs.openapi.domain.service;

import com.papaolabs.openapi.domain.model.Breed;
import com.papaolabs.openapi.domain.model.Region;
import com.papaolabs.openapi.domain.model.Shelter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class CaptureServiceImpl implements CaptureService {
    public static final String DEFAULT_BREED_TB_NAME = "animal_kind_tb";
    public static final String DEFAULT_REGION_TB_NAME = "region_tb";
    public static final String DEFAULT_SHELTER_TB_NAME = "animal_shelter_tb";
    @NotNull
    private final OperationService operationService;

    public CaptureServiceImpl(OperationService operationService) {
        this.operationService = operationService;
    }

    @Override
    public String captureBreedList(String tableName) {
        String tbName = isNotEmpty(tableName) ? tableName : DEFAULT_BREED_TB_NAME;
        List<Breed> breedList = this.operationService.getBreedList()
                                                     .stream()
                                                     .sorted(Comparator.comparing(Breed::getCode))
                                                     .filter(distinctByKey(Breed::getName))
                                                     .collect(Collectors.toList());
        for (int i = 0; i < breedList.size(); i++) {
            breedList.get(i)
                     .setId(Long.valueOf(i + 1));
        }
        return breedList.stream()
                        .map(x -> "insert into " + tbName
                            + " (id, up_kind_code, kind_code, kind_name, created_date, updated_date) VALUES" +
                            " (" + x.getId() + ", " + x.getSpecies()
                                                       .getCode() + ", " + x.getCode() + ", '" + x.getName() + "', CURRENT_TIMESTAMP, " +
                            "CURRENT_TIMESTAMP);\n"
                        )
                        .collect(Collectors.joining());
    }

    @Override
    public String captureRegionList(String tableName) {
        String tbName = isNotEmpty(tableName) ? tableName : DEFAULT_REGION_TB_NAME;
        List<Region> regionList = this.operationService.getRegionList();
        for (int i = 0; i < regionList.size(); i++) {
            regionList.get(i)
                      .setId(Long.valueOf(i + 1));
        }
        return regionList
            .stream()
            .map(x -> "insert into " + tbName + " (id, sido_code, sido_name, gungu_code, gungu_name, " +
                "created_date, updated_date) VALUES (" + x.getId() + ", " + x.getSidoCode() + ", '" + x.getSidoName()
                + "', " + x.getGunguCode() + ", '" + x.getGunguName() + "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)" +
                ";\n")
            .collect(Collectors.joining());
    }

    @Override
    public String captureShelterList(String tableName) {
        String tbName = isNotEmpty(tableName) ? tableName : DEFAULT_SHELTER_TB_NAME;
        List<Region> regionResults = this.operationService.getRegionList();
        for (int i = 0; i < regionResults.size(); i++) {
            regionResults.get(i)
                         .setId(Long.valueOf(i + 1));
        }
        List<Shelter> shelterList = this.operationService.getShelterList();
        List<Shelter> shelterResults = new ArrayList();
        for (int i = 0; i < shelterList.size(); i++) {
            Shelter shelter = shelterList.get(i);
            Shelter result = new Shelter();
            for (Region region : regionResults) {
                if (region.getGunguCode()
                          .equals(shelter.getRegion()
                                         .getGunguCode())) {
                    result.setRegion(region);
                }
            }
            result.setId(Long.valueOf(i + 1));
            result.setCode(shelter.getCode());
            result.setName(shelter.getName());
            shelterResults.add(result);
        }
        return this.operationService.getShelterList()
                                    .stream()
                                    .map(x -> {
                                        Long id = 0L;
                                        for (Region region : regionResults) {
                                            if (region.getGunguCode()
                                                      .equals(x.getRegion()
                                                               .getGunguCode())) {
                                                id = region.getId();
                                            }
                                        }
                                        return "insert into " + tbName + " (id, region_id, code, name, " +
                                            "created_date, updated_date) VALUES (" + x.getId() + ", " + id + ", '" + x.getCode()
                                            + "', '" + x.getName() + "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);\n";
                                    })
                                    .collect(Collectors.joining());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
