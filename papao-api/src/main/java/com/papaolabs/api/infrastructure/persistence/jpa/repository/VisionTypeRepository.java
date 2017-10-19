package com.papaolabs.api.infrastructure.persistence.jpa.repository;

import com.papaolabs.api.domain.model.VisionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisionTypeRepository extends JpaRepository<VisionType, Long> {
}
