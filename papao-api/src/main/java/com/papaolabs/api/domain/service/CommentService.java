package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.controller.response.CommentDTO;
import com.papaolabs.api.interfaces.v1.controller.response.ResponseType;

public interface CommentService {
    ResponseType create(String postId, String userId, String text);
//    CommentDTO createByGuest(String postId, String text);

    ResponseType delete(String commentId);

    CommentDTO readComments(String postId);
//    CommentDTO readComment(String postId, String commentId);
}
