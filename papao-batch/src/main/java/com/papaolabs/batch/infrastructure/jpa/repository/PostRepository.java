package com.papaolabs.batch.infrastructure.jpa.repository;

import com.papaolabs.batch.infrastructure.jpa.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(Date beginDate, Date endDate);

    List<Post> findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(Date beginDate, Date endDate, Pageable pageable);
}
