package com.papaolabs.api.infrastructure.persistence.jpa.repository;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findBySidoCodeAndGunguCode(Long sidoCode, Long gunguCode);
}
