package com.papaolabs.openapi.domain.service;

import com.papaolabs.openapi.domain.model.Animal;
import com.papaolabs.openapi.domain.model.Breed;
import com.papaolabs.openapi.domain.model.Region;
import com.papaolabs.openapi.domain.model.Shelter;

import java.util.List;

public interface OperationService {
    List<Animal> getAnimalList(String beginDate,
                               String endDate,
                               String speciesCode,
                               String kindCode,
                               String sidoCode,
                               String gunguCode,
                               String shelterCode,
                               String state,
                               String index,
                               String size);

    List<Breed> getBreedList();

    List<Region> getRegionList();

    List<Shelter> getShelterList();
}
