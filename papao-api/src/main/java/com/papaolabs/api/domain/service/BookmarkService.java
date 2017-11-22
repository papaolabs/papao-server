package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.controller.response.BookmarkDTO;

import java.util.List;

public interface BookmarkService {
    Long registerBookmark(String postId, String userId);

    Long cancelBookmark(String postId, String userId);

    Long countBookmark(String postId);

    BookmarkDTO readBookmarkByUserId(String userId, String index, String size);

    BookmarkDTO readBookmarkByPostId(String postId, String index, String size);

    Boolean checkBookmark(String postId, String userId);
}
