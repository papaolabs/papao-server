package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.feign.openapi.PushApiClient;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Comment;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.User;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BreedRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.CommentRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.UserRepository;
import com.papaolabs.api.interfaces.v1.controller.response.CommentDTO;
import com.papaolabs.api.interfaces.v1.controller.response.ResponseType;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Slf4j
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
    @NotNull
    private final PostRepository postRepository;
    @NotNull
    private final PushApiClient pushApiClient;

    public CommentServiceImpl(CommentRepository commentRepository,
                              BreedRepository breedRepository,
                              UserRepository userRepository,
                              PostRepository postRepository,
                              PushApiClient pushApiClient) {
        this.commentRepository = commentRepository;
        this.breedRepository = breedRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.pushApiClient = pushApiClient;
    }

    @Override
    public ResponseType create(String postId, String userId, String text) {
        Comment comment = new Comment();
        comment.setPostId(Long.valueOf(postId));
        comment.setUserId(userId);
        comment.setText(text);
        comment.setDisplay(TRUE);
        Post post = postRepository.findOne(Long.valueOf(postId));
        post.getComments()
            .add(comment);
        postRepository.save(post);
        User user = userRepository.findByUid(userId);
        String message = StringUtils.join("\\ud83d\\udc36", user.getNickName(), "님이 댓글을 남겼습니다 : ", StringUtils.left(text, 20), text.length() > 20 ? "..." : "");
        try {
            pushApiClient.sendPush("POST", String.valueOf(post.getUid()), message, postId);
        } catch (FeignException fe) {
        }
        return ResponseType.builder()
                           .code(ResponseType.ResponseCode.SUCCESS.getCode())
                           .name(ResponseType.ResponseCode.SUCCESS.name())
                           .build();
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
    public ResponseType delete(String commentId) {
        Comment comment = commentRepository.findOne(Long.valueOf(commentId));
        if (comment == null) {
            return ResponseType.builder()
                               .code(ResponseType.ResponseCode.NOTFOUND.getCode())
                               .name(ResponseType.ResponseCode.NOTFOUND.name())
                               .build();
        }
        comment.setDisplay(FALSE);
        commentRepository.save(comment);
        return ResponseType.builder()
                           .code(ResponseType.ResponseCode.SUCCESS.getCode())
                           .name(ResponseType.ResponseCode.SUCCESS.name())
                           .build();
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
        if (user == null) {
            user = new User();
            user.setNickName("탈퇴회원");
            user.setProfileUrl("http://220.230.121.76:8000/v1/download/86d5b5dca78b4d908f7032df35d53c9e.png");
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
