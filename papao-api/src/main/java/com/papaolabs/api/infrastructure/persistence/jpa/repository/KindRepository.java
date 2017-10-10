package com.papaolabs.api.infrastructure.persistence.jpa.repository;

import com.papaolabs.api.domain.model.Kind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KindRepository extends JpaRepository<Kind, Long> {
    Kind findByKindName(String kindName);
}
