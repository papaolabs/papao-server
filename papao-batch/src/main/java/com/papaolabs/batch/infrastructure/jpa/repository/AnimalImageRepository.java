package com.papaolabs.batch.infrastructure.jpa.repository;

import com.papaolabs.batch.infrastructure.jpa.entity.AnimalImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalImageRepository extends JpaRepository<AnimalImage, Long> {
}
