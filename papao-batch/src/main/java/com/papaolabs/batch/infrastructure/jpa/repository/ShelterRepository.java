package com.papaolabs.batch.infrastructure.jpa.repository;

import com.papaolabs.batch.infrastructure.jpa.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    List<Shelter> findBySidoCode(Long cityCode);

    List<Shelter> findByGunguCode(Long townCode);

    List<Shelter> findBySidoName(String cityName);

    List<Shelter> findByGunguName(String townName);

    Shelter findByShelterCode(String shelterCode);

    List<Shelter> findByShelterName(String shelterName);
}
