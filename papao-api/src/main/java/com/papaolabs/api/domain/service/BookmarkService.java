package com.papaolabs.api.domain.service;

public interface BookmarkService {
    Long registerBookmark(String postId, String userId);

    Long cancelBookmark(String postId, String userId);

    Long countBookmark(String postId);
}
