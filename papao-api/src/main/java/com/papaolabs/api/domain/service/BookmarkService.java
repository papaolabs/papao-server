package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.controller.response.BookmarkDTO;
import com.papaolabs.api.interfaces.v1.controller.response.ResponseType;

public interface BookmarkService {
    ResponseType registerBookmark(String postId, String userId);

    ResponseType cancelBookmark(String postId, String userId);

    Long countBookmark(String postId);

    BookmarkDTO readBookmarkByPostId(String postId, String index, String size);

    Boolean checkBookmark(String postId, String userId);
}
