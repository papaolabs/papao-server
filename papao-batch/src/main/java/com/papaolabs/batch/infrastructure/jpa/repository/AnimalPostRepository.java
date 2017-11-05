package com.papaolabs.batch.infrastructure.jpa.repository;

import com.papaolabs.batch.infrastructure.jpa.entity.AnimalPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AnimalPostRepository extends JpaRepository<AnimalPost, Long> {
    List<AnimalPost> findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(Date beginDate, Date endDate);
}
