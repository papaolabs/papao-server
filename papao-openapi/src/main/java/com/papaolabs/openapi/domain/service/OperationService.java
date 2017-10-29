package com.papaolabs.openapi.domain.service;

import com.papaolabs.openapi.domain.model.Breed;
import com.papaolabs.openapi.domain.model.Region;
import com.papaolabs.openapi.domain.model.Shelter;

import java.util.List;

public interface OperationService {
    List<Breed> getBreedList();

    List<Region> getRegionList();

    List<Shelter> getShelterList();
}
