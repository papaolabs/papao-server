package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO create(String postId, String userId, String userName, String text);

    CommentDTO delete(String commentId);

    List<CommentDTO> readComments(String postId);

    CommentDTO readComment(String commentId);
}
