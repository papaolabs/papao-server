package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Bookmark;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    @NotNull
    private BookmarkRepository bookmarkRepository;

    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public Long registerBookmark(Long postId, Long userId) {
        Bookmark bookmark = new Bookmark();
        bookmark.setPostId(postId);
        bookmark.setUserId(userId);
        this.bookmarkRepository.save(bookmark);
        return this.bookmarkRepository.countByPostId(postId);
    }

    @Override
    public Long cancelBookmark(Long postId, Long userId) {
        this.bookmarkRepository.deleteByPostIdAndUserId(postId, userId);
        return this.bookmarkRepository.countByPostId(postId);
    }

    @Override
    public Long countBookmark(Long postId) {
        return this.bookmarkRepository.countByPostId(postId);
    }
}
