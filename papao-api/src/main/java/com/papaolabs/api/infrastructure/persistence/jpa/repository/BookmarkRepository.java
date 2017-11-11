package com.papaolabs.api.infrastructure.persistence.jpa.repository;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Long countByPostId(Long postId);
    Long deleteByPostIdAndUserId(Long postId, Long userId);
}
