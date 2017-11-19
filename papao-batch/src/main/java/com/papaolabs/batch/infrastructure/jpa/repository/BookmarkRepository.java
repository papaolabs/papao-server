package com.papaolabs.batch.infrastructure.jpa.repository;

import com.papaolabs.batch.infrastructure.jpa.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Long countByPostId(Long postId);
    Long deleteByPostIdAndUserId(Long postId, Long userId);
    Bookmark findByPostIdAndUserId(Long postId, Long userId);
    List<Bookmark> findByPostId(Long postId);
}
