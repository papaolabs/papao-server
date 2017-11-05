package com.papaolabs.batch.infrastructure.jpa.repository;

import com.papaolabs.batch.infrastructure.jpa.entity.AnimalPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalPostRepository extends JpaRepository<AnimalPost, Long> {
}
