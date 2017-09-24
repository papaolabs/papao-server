package com.papaolabs.api.infrastructure.persistence.jpa.repository;

import com.papaolabs.api.domain.model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Shelter findByCityCode(String cityCode);
    Shelter findByTownCode(String townCode);
}
