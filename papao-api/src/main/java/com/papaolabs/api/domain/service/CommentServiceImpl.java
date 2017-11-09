package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Comment;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Breed;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.CommentRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BreedRepository;
import com.papaolabs.api.interfaces.v1.dto.CommentDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private static final String DATE_FORMAT = "yyyyMMdd";
    @NotNull
    private final CommentRepository commentRepository;
    @NotNull
    private final BreedRepository breedRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BreedRepository breedRepository) {
        this.commentRepository = commentRepository;
        this.breedRepository = breedRepository;
    }

    @Override
    public CommentDTO create(String postId, String userId, String text) {
        Comment comment = new Comment();
        comment.setPostId(Long.valueOf(postId));
        comment.setUserId(userId);
        comment.setText(text);
        return transform(commentRepository.save(comment));
    }

    @Override
    public CommentDTO createByGuest(String postId, String text) {
        List<Breed> kind = breedRepository.findAll();
        Random random = new Random();
        Comment comment = new Comment();
        comment.setPostId(Long.valueOf(postId));
        comment.setUserId("-1");
/*        comment.setUserName(kind.get(random.nextInt(kind.size()))
                                .getKindName());*/
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
                comment.setDisplay(FALSE);
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
        return commentRepository.findByPostId(Long.valueOf(postId))
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
                         .text(comment.getText())
                         .createdDate(comment.getCreatedDateTime()
                                             .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                         .lastModifiedDate(comment.getLastModifiedDateTime()
                                                  .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                         .build();
    }
}
