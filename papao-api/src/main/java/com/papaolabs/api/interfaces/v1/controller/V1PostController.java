package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.CommentService;
import com.papaolabs.api.domain.service.PostService;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.interfaces.v1.dto.CommentDTO;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin(origins = "*")
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
    public ResponseEntity<PostDTO> createPost(@RequestParam String happenDate,
                                              @RequestParam String happenPlace,
                                              @RequestParam String uid,
                                              @RequestParam String postType,
                                              @RequestParam List<String> imageUrls,
                                              @RequestParam(defaultValue = "417000", required = false) Long upKindCode,
                                              @RequestParam(defaultValue = "115", required = false) Long kindCode,
                                              @RequestParam(defaultValue = "-1", required = false) String contact,
                                              @RequestParam(defaultValue = "U", required = false) String genderType,
                                              @RequestParam(defaultValue = "U", required = false) String neuterType,
                                              @RequestParam(defaultValue = "-1", required = false) Integer age,
                                              @RequestParam(defaultValue = "-1", required = false) Float weight,
                                              @RequestParam(defaultValue = "", required = false) String feature,
                                              @RequestParam(defaultValue = "9999999", required = false) Long sidoCode,
                                              @RequestParam(defaultValue = "9999999", required = false) Long gunguCode
    ) {
        return new ResponseEntity<>(postService.create(happenDate,
                                                       happenPlace,
                                                       uid,
                                                       postType,
                                                       imageUrls,
                                                       upKindCode,
                                                       kindCode,
                                                       contact,
                                                       genderType,
                                                       neuterType,
                                                       age,
                                                       weight,
                                                       feature,
                                                       sidoCode,
                                                       gunguCode), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> readPosts(@RequestParam(required = false) String beginDate,
                                                   @RequestParam(required = false) String endDate,
                                                   @RequestParam(required = false) String upKindCode,
                                                   @RequestParam(required = false) String kindCode,
                                                   @RequestParam(required = false) String sidoCode,
                                                   @RequestParam(required = false) String gunguCode
    ) {
        return new ResponseEntity(postService.readPosts(beginDate, endDate, upKindCode
            , kindCode, sidoCode, gunguCode), HttpStatus.OK);
    }

    @GetMapping("/pages")
    public ResponseEntity<List<PostDTO>> readPostsByPage(@RequestParam(required = false) String beginDate,
                                                         @RequestParam(required = false) String endDate,
                                                         @RequestParam(required = false) String upKindCode,
                                                         @RequestParam(required = false) String kindCode,
                                                         @RequestParam(required = false) String sidoCode,
                                                         @RequestParam(required = false) String gunguCode,
                                                         @RequestParam(defaultValue = "0", required = false) String index,
                                                         @RequestParam(defaultValue = "100", required = false) String size
    ) {
        return new ResponseEntity(postService.readPostsByPage(beginDate, endDate, upKindCode
            , kindCode, sidoCode, gunguCode, index, size), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> readPost(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(postService.readPost(postId), HttpStatus.OK);
    }

    @PostMapping("/{postId}/state")
    public ResponseEntity<PostDTO> setStatus(@PathVariable("postId") String postId, @RequestParam Post.StateType state) {
        return new ResponseEntity<>(postService.setState(postId, state), HttpStatus.OK);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<PostDTO> deletePost(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(postService.delete(postId), HttpStatus.OK);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") String postId,
                                                    @RequestParam("userId") String userId,
                                                    @RequestParam("text") String text) {
        return new ResponseEntity<>(commentService.create(postId, userId, text), HttpStatus.OK);
    }

    @PostMapping("/{postId}/comments/guest")
    public ResponseEntity<CommentDTO> createCommentByGuest(@PathVariable("postId") String postId,
                                                           @RequestParam("text") String text) {
        return new ResponseEntity<>(commentService.createByGuest(postId, text), HttpStatus.OK);
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
        return new ResponseEntity<>(commentService.readComment(postId, commentId), HttpStatus.OK);
    }
}
