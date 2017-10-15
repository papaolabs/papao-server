package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.Comment;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.CommentRepository;
import com.papaolabs.api.interfaces.v1.dto.CommentDTO;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;

@Service
public class CommentServiceImpl implements CommentService {
    private static final String DATE_FORMAT = "yyyyMMdd";
    @NotNull
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDTO create(String postId, String userId, String userName, String text) {
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setUserName(userName);
        comment.setText(text);
        return transform(commentRepository.save(comment));
    }

    @Override
    public CommentDTO delete(String postId, String commentId, String userId) {
        Comment comment = commentRepository.findOne(Long.valueOf(commentId));
        if (comment != null) {
            if (comment.getPostId()
                       .equals(postId) && comment.getUserId()
                                                 .equals(userId)) {
                comment.setIsDisplay(FALSE);
                commentRepository.save(comment);
                return transform(comment);
            }
        }
        comment = new Comment();
        comment.setId(-1L);
        return transform(comment);
    }

    @Override
    public List<CommentDTO> readComments(String postId) {
        return commentRepository.findByPostId(postId)
                                .stream()
                                .map(this::transform)
                                .sorted(Comparator.comparing(CommentDTO::getCreatedDate))
                                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO readComment(String postId, String commentId) {
        Comment comment = commentRepository.findOne(Long.valueOf(commentId));
        if (comment != null) {
            if (comment.getPostId()
                       .equals(postId)) {
                return transform(comment);
            }
        }
        comment = new Comment();
        comment.setId(-1L);
        return transform(comment);
    }

    private CommentDTO transform(Comment comment) {
        return CommentDTO.builder()
                         .id(comment.getId())
                         .postId(Long.valueOf(comment.getPostId()))
                         .userId(comment.getUserId())
                         .userName(comment.getUserName())
                         .text(comment.getText())
                         .createdDate(convertDateToString(comment.getCreatedDate()))
                         .lastModifiedDate(convertDateToString(comment.getLastModifiedDate()))
                         .build();
    }

    private String convertDateToString(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat(DATE_FORMAT);
        return transFormat.format(date);
    }

    private Date convertStringToDate(String from) {
        SimpleDateFormat transFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return transFormat.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
