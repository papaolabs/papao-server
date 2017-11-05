package com.papaolabs.batch.infrastructure.jpa.repository;

import com.papaolabs.batch.infrastructure.jpa.entity.AbandonedAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbandonedAnimalRepository extends JpaRepository<AbandonedAnimal, Long> {
}
