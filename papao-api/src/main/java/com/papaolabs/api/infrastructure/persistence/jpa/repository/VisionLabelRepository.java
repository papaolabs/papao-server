package com.papaolabs.api.infrastructure.persistence.jpa.repository;

import com.papaolabs.api.domain.model.VisionLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisionLabelRepository extends JpaRepository<VisionLabel, Long> {
}
