package com.papaolabs.api.infrastructure.persistence.jpa.repository;

import com.papaolabs.api.domain.model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    List<Shelter> findByCityCode(Long cityCode);
    List<Shelter> findByTownCode(Long townCode);
    List<Shelter> findByCityName(String cityName);
    List<Shelter> findByTownName(String townName);
}
