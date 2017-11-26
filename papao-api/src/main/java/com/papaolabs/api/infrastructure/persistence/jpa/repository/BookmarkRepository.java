package com.papaolabs.api.infrastructure.persistence.jpa.repository;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Long countByPostId(Long postId);
    Long deleteByPostIdAndUserId(Long postId, String userId);
    Bookmark findByPostIdAndUserId(Long postId, String userId);
    List<Bookmark> findByPostId(Long postId);
    Page<Bookmark> findByPostId(Long postId, Pageable pageable);
    Page<Bookmark> findByUserId(String userId, Pageable pageable);
}
