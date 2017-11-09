package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO create(String postId, String userId, String text);

    CommentDTO createByGuest(String postId, String text);

    CommentDTO delete(String postId, String commentId, String userId);

    List<CommentDTO> readComments(String postId);

    CommentDTO readComment(String postId, String commentId);
}
