package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Bookmark;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    @NotNull
    private BookmarkRepository bookmarkRepository;

    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public Long registerBookmark(String postId, String userId) {
        Bookmark bookmark = new Bookmark();
        bookmark.setPostId(Long.valueOf(postId));
        bookmark.setUserId(Long.valueOf(userId));
        this.bookmarkRepository.save(bookmark);
        return this.bookmarkRepository.countByPostId(Long.valueOf(postId));
    }

    @Transactional
    @Override
    public Long cancelBookmark(String postId, String userId) {
        this.bookmarkRepository.deleteByPostIdAndUserId(Long.valueOf(postId), Long.valueOf(userId));
        return this.bookmarkRepository.countByPostId(Long.valueOf(postId));
    }

    @Override
    public Long countBookmark(String postId) {
        return this.bookmarkRepository.countByPostId(Long.valueOf(postId));
    }
}
