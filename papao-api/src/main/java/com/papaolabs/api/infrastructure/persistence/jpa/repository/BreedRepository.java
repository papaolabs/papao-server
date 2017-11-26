package com.papaolabs.api.infrastructure.persistence.jpa.repository;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {
    Breed findByKindName(String kindName);
    Breed findByKindCode(Long kindCode);
    List<Breed> findByUpKindCode(Long kindCode);
}
