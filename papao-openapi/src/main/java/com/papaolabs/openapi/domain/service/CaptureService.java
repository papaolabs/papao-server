package com.papaolabs.openapi.domain.service;

public interface CaptureService {
    String captureBreedList(String tableName);

    String captureRegionList(String tableName);

    String captureShelterList(String tableName);
}
