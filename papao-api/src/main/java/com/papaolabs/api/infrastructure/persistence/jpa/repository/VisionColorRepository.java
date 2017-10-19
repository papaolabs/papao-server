package com.papaolabs.api.infrastructure.persistence.jpa.repository;

import com.papaolabs.api.domain.model.VisionColor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisionColorRepository extends JpaRepository<VisionColor, Long> {
}
