package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.dto.KindDTO;

import java.util.List;

public interface AnimalService {
    List<KindDTO> getKindList();
}
