package com.papaolabs.batch.infrastructure.jpa.repository;

import com.papaolabs.batch.infrastructure.jpa.entity.AnimalKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalKindRepository extends JpaRepository<AnimalKind, Long> {
}
