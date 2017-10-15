package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.CommentService;
import com.papaolabs.api.domain.service.PostService;
import com.papaolabs.api.interfaces.v1.dto.CommentDTO;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import com.papaolabs.api.interfaces.v1.dto.type.StateType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class V1PostController {
    @NotNull
    private final PostService postService;
    @NotNull
    private final CommentService commentService;

    public V1PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> create(@RequestParam(required = false) String happenDate,
                                          @RequestParam(required = false) String happenPlace,
                                          @RequestParam(required = false) String uprCode,
                                          @RequestParam(required = false) String orgCode,
                                          @RequestParam(required = false) String uid,
                                          @RequestParam(required = false) String contracts,
                                          @RequestParam(required = false) String postType,
                                          @RequestParam(required = false) String imageUrl,
                                          @RequestParam(required = false) String gender,
                                          @RequestParam(required = false) String neuter,
                                          @RequestParam(required = false) String kindUpCode,
                                          @RequestParam(required = false) String kindCode,
                                          @RequestParam(required = false) String age,
                                          @RequestParam(required = false) Float weight,
                                          @RequestParam(required = false) String introduction,
                                          @RequestParam(required = false) String feature
    ) {
        return new ResponseEntity<>(postService.create(
            imageUrl,
            postType,
            gender,
            neuter,
            uid,
            contracts,
            happenDate,
            happenPlace,
            uprCode,
            orgCode,
            kindUpCode,
            kindCode,
            age,
            weight,
            introduction,
            feature
        ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> read(@RequestParam(required = false) String beginDate,
                                              @RequestParam(required = false) String endDate,
                                              @RequestParam(required = false) String kindUpCode,
                                              @RequestParam(required = false) String uprCode,
                                              @RequestParam(required = false) String orgCode
    ) {
        return new ResponseEntity<>(postService.readPosts(beginDate, endDate, kindUpCode, uprCode, orgCode), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> read(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(postService.readPost(postId), HttpStatus.OK);
    }

    @PostMapping("/{postId}/state")
    public ResponseEntity<PostDTO> status(@PathVariable("postId") String postId, @RequestParam StateType state) {
        return new ResponseEntity<>(postService.setState(postId, state), HttpStatus.OK);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<PostDTO> delete(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(postService.delete(postId), HttpStatus.OK);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") String postId,
                                                    @RequestParam("userId") String userId,
                                                    @RequestParam("userName") String userName,
                                                    @RequestParam("text") String text) {
        return new ResponseEntity<>(commentService.create(postId, userId, userName, text), HttpStatus.OK);
    }

    @PostMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> deleteComment(@PathVariable("postId") String postId,
                                                    @PathVariable("commentId") String commentId,
                                                    @RequestParam("userId") String userId) {
        return new ResponseEntity<>(commentService.delete(postId, commentId, userId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> readComments(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(commentService.readComments(postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> readComment(@PathVariable("postId") String postId,
                                                  @PathVariable("commentId") String commentId) {
        return new ResponseEntity<>(commentService.readComment(postId, commentId));
    }
}
