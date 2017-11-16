package com.papaolabs.batch.infrastructure.jpa.repository;

import com.papaolabs.batch.infrastructure.jpa.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select p from Post p where ?1 >= p.happenDate and ?2 <= p.happenDate")
    List<Post> findByHappenDate(Date beginDate, Date endDate);
    List<Post> findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(Date beginDate, Date endDate);
    Post findByDesertionId(String desertionId);
}
