package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.BookmarkService;
import com.papaolabs.api.domain.service.CommentService;
import com.papaolabs.api.domain.service.PostService;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.interfaces.v1.dto.CommentDTO;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import com.papaolabs.api.interfaces.v1.dto.PostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @NotNull
    private final BookmarkService bookmarkService;

    public V1PostController(PostService postService,
                            CommentService commentService,
                            BookmarkService bookmarkService) {
        this.postService = postService;
        this.commentService = commentService;
        this.bookmarkService = bookmarkService;
    }

    /*
        Posts
    */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDTO> createPost(@RequestBody PostRequest postRequest
    ) {
        return new ResponseEntity<>(postService.create(postRequest.getHappenDate(),
                                                       postRequest.getHappenPlace(),
                                                       postRequest.getUid(),
                                                       postRequest.getPostType(),
                                                       postRequest.getStateType(),
                                                       postRequest.getImageUrls(),
                                                       postRequest.getUpKindCode(),
                                                       postRequest.getKindCode(),
                                                       postRequest.getContact(),
                                                       postRequest.getGenderType(),
                                                       postRequest.getNeuterType(),
                                                       postRequest.getAge(),
                                                       postRequest.getWeight(),
                                                       postRequest.getFeature(),
                                                       postRequest.getSidoCode(),
                                                       postRequest.getGunguCode()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> readPosts(@RequestParam(required = false) String postType,
                                                   @RequestParam(required = false) String beginDate,
                                                   @RequestParam(required = false) String endDate,
                                                   @RequestParam(required = false) String upKindCode,
                                                   @RequestParam(required = false) String kindCode,
                                                   @RequestParam(required = false) String sidoCode,
                                                   @RequestParam(required = false) String gunguCode
    ) {
        return new ResponseEntity(postService.readPosts(postType, beginDate, endDate, upKindCode
            , kindCode, sidoCode, gunguCode), HttpStatus.OK);
    }

    @GetMapping("/pages")
    public ResponseEntity<List<PostDTO>> readPostsByPage(@RequestParam(required = false) String postType,
                                                         @RequestParam(required = false) String beginDate,
                                                         @RequestParam(required = false) String endDate,
                                                         @RequestParam(required = false) String upKindCode,
                                                         @RequestParam(required = false) String kindCode,
                                                         @RequestParam(required = false) String sidoCode,
                                                         @RequestParam(required = false) String gunguCode,
                                                         @RequestParam(defaultValue = "0", required = false) String index,
                                                         @RequestParam(defaultValue = "100", required = false) String size
    ) {
        return new ResponseEntity(postService.readPostsByPage(postType, beginDate, endDate, upKindCode
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

    /*
        Comments
     */
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") String postId,
                                                    @RequestParam("userId") String userId,
                                                    @RequestParam("text") String text) {
        return new ResponseEntity<>(commentService.create(postId, userId, text), HttpStatus.OK);
    }

    @PostMapping("/comments/{commentId}")
    public ResponseEntity<CommentDTO> deleteComment(@PathVariable("commentId") String commentId) {
        commentService.delete(commentId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> readComments(@PathVariable("postId") String postId) {
        return new ResponseEntity(commentService.readComments(postId), HttpStatus.OK);
    }

    /*
        Bookmark
     */
    @PostMapping("/{postId}/bookmarks")
    public ResponseEntity<Long> registBookmark(@PathVariable("postId") String postId, @RequestParam("userId") String userId) {
        return new ResponseEntity(bookmarkService.registerBookmark(postId, userId), HttpStatus.OK);
    }

    @PostMapping("/{postId}/bookmarks/cancel")
    public ResponseEntity<Long> cancelBookmark(@PathVariable("postId") String postId, @RequestParam("userId") String userId) {
        return new ResponseEntity(bookmarkService.cancelBookmark(postId, userId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/bookmarks/count")
    public ResponseEntity<Long> countBookmark(@PathVariable("postId") String postId) {
        return new ResponseEntity(bookmarkService.countBookmark(postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/bookmarks/check")
    public ResponseEntity<Boolean> checkBookmark(@PathVariable("postId") String postId, @RequestParam("userId") String userId) {
        return new ResponseEntity(bookmarkService.checkBookmark(postId, userId), HttpStatus.OK);
    }
}
