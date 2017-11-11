package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Bookmark;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    @NotNull
    private BookmarkRepository bookmarkRepository;

    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public Long registerBookmark(String postId, String userId) {
        Bookmark bookmark = bookmarkRepository.findByPostIdAndUserId(Long.valueOf(postId), Long.valueOf(userId));
        if (Objects.isNull(bookmark)) {
            bookmark.setPostId(Long.valueOf(postId));
            bookmark.setUserId(Long.valueOf(userId));
            this.bookmarkRepository.save(bookmark);
        }
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

    @Override
    public Boolean checkBookmark(String postId, String userId) {
        Bookmark bookmark = this.bookmarkRepository.findByPostIdAndUserId(Long.valueOf(postId), Long.valueOf(userId));
        return Objects.isNull(bookmark) ? FALSE : TRUE;
    }
}
