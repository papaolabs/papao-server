package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Comment;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.User;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BreedRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.CommentRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.UserRepository;
import com.papaolabs.api.interfaces.v1.controller.response.CommentDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
    @NotNull
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              BreedRepository breedRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.breedRepository = breedRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentDTO create(String postId, String userId, String text) {
        Comment comment = new Comment();
        comment.setPostId(Long.valueOf(postId));
        comment.setUserId(userId);
        comment.setText(text);
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPostId(Long.valueOf(postId));
        commentDTO.setContents(Arrays.asList(transform(commentRepository.save(comment))));
        return commentDTO;
    }

    /*@Override
    public CommentDTO createByGuest(String postId, String text) {
        List<Breed> kind = breedRepository.findAll();
        Random random = new Random();
        Comment comment = new Comment();
        comment.setPostId(Long.valueOf(postId));
        comment.setUserId("-1");
*//*        comment.setUserName(kind.get(random.nextInt(kind.size()))
                                .getKindName());*//*
        comment.setText(text);
        return transform(commentRepository.save(comment));
    }*/

    @Override
    public void delete(String commentId) {
        Comment comment = commentRepository.findOne(Long.valueOf(commentId));
        comment.setDisplay(FALSE);
        commentRepository.save(comment);
    }

    @Override
    public CommentDTO readComments(String postId) {
        List<CommentDTO.Content> contents = commentRepository.findByPostId(Long.valueOf(postId))
                                                             .stream()
                                                             .map(this::transform)
                                                             .sorted(Comparator.comparing(CommentDTO.Content::getCreatedDate))
                                                             .collect(Collectors.toList());
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPostId(Long.valueOf(postId));
        commentDTO.setContents(contents);
        return commentDTO;
    }

/*    @Override
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
    }*/

    private CommentDTO.Content transform(Comment comment) {
        User user = userRepository.findByUid(comment.getUserId());
        if(user == null) {
            user.setNickName("탈퇴회원");
            user.setProfileUrl("https://photos.app.goo.gl/JG1eawv9DMcyDcnh2");
        }
        CommentDTO.Content content = new CommentDTO.Content();
        content.setId(comment.getId());
        content.setUserId(comment.getUserId());
        content.setNickname(user.getNickName());
        content.setProfileUrl(user.getProfileUrl());
        content.setText(comment.getText());
        content.setCreatedDate(comment.getCreatedDateTime()
                                      .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        content.setLastModifiedDate(comment.getLastModifiedDateTime()
                                           .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return content;
    }
}
