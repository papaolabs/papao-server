package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.CommentService;
import com.papaolabs.api.domain.service.PostService;
import com.papaolabs.api.interfaces.v1.dto.CommentDTO;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import com.papaolabs.api.interfaces.v1.dto.type.StateType;
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
                                              @RequestParam(required = false) String kindUpCode,
                                              @RequestParam(required = false) String kindCode,
                                              @RequestParam(required = false) String contact,
                                              @RequestParam(required = false) String gender,
                                              @RequestParam(required = false) String neuter,
                                              @RequestParam(required = false) String age,
                                              @RequestParam(required = false) Float weight,
                                              @RequestParam(required = false) String feature,
                                              @RequestParam(required = false) String uprCode,
                                              @RequestParam(required = false) String orgCode
    ) {
        return new ResponseEntity<>(postService.create(happenDate,
                                                       happenPlace,
                                                       uid,
                                                       postType,
                                                       imageUrls,
                                                       kindUpCode,
                                                       kindCode,
                                                       contact,
                                                       gender,
                                                       neuter,
                                                       age,
                                                       weight,
                                                       feature,
                                                       uprCode,
                                                       orgCode), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> readPosts(@RequestParam(required = false) String beginDate,
                                                   @RequestParam(required = false) String endDate,
                                                   @RequestParam(required = false) String kindUpCode,
                                                   @RequestParam(required = false) String uprCode,
                                                   @RequestParam(required = false) String orgCode,
                                                   @RequestParam(defaultValue = "1", required = false) String index,
                                                   @RequestParam(defaultValue = "100", required = false) String size
    ) {
        return new ResponseEntity<>(postService.readPosts(beginDate, endDate, kindUpCode, uprCode, orgCode, index, size), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> readPost(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(postService.readPost(postId), HttpStatus.OK);
    }

    @PostMapping("/{postId}/state")
    public ResponseEntity<PostDTO> setStatus(@PathVariable("postId") String postId, @RequestParam StateType state) {
        return new ResponseEntity<>(postService.setState(postId, state), HttpStatus.OK);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<PostDTO> deletePost(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(postService.delete(postId), HttpStatus.OK);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") String postId,
                                                    @RequestParam("userId") String userId,
                                                    @RequestParam("userName") String userName,
                                                    @RequestParam("text") String text) {
        return new ResponseEntity<>(commentService.create(postId, userId, userName, text), HttpStatus.OK);
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
