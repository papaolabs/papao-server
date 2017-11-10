package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.dto.CommentDTO;

public interface CommentService {
    CommentDTO create(String postId, String userId, String text);
//    CommentDTO createByGuest(String postId, String text);

    void delete(String commentId);

    CommentDTO readComments(String postId);
//    CommentDTO readComment(String postId, String commentId);
}
