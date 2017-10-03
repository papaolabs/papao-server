package com.papaolabs.api.infrastructure.persistence.jpa.repository;

import com.papaolabs.api.domain.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
}
