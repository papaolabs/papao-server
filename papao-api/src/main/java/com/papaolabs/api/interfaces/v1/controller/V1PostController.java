package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.BookmarkService;
import com.papaolabs.api.domain.service.CommentService;
import com.papaolabs.api.domain.service.PostService;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.interfaces.v1.controller.request.CommentRequest;
import com.papaolabs.api.interfaces.v1.controller.request.PostRequest;
import com.papaolabs.api.interfaces.v1.controller.response.CommentDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PostDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PostPreviewDTO;
import com.papaolabs.api.interfaces.v1.controller.response.ResponseType;
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

import javax.validation.Valid;
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
    public ResponseEntity<ResponseType> createPost(@RequestBody @Valid PostRequest postRequest
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

    /*@GetMapping
    public ResponseEntity<List<PostDTO>> readPosts(@RequestParam(required = false) List<String> postType,
                                                   @RequestParam(required = false) String beginDate,
                                                   @RequestParam(required = false) String endDate,
                                                   @RequestParam(required = false) String upKindCode,
                                                   @RequestParam(required = false) String kindCode,
                                                   @RequestParam(required = false) String sidoCode,
                                                   @RequestParam(required = false) String gunguCode,
                                                   @RequestParam(required = false) String genderType,
                                                   @RequestParam(required = false) String neuterType
    ) {
        return new ResponseEntity(postService.readPosts(postType, beginDate, endDate, upKindCode
            , kindCode, sidoCode, gunguCode, genderType, neuterType), HttpStatus.OK);
    }*/

    @GetMapping("/pages")
    public ResponseEntity<PostPreviewDTO> readPostsByPage(@RequestParam(required = false) List<String> postType,
                                                          @RequestParam(required = false) String userId,
                                                          @RequestParam(required = false) String beginDate,
                                                          @RequestParam(required = false) String endDate,
                                                          @RequestParam(required = false) String upKindCode,
                                                          @RequestParam(required = false) String kindCode,
                                                          @RequestParam(required = false) String sidoCode,
                                                          @RequestParam(required = false) String gunguCode,
                                                          @RequestParam(required = false) String genderType,
                                                          @RequestParam(required = false) String neuterType,
                                                          @RequestParam(defaultValue = "0", required = false) String index,
                                                          @RequestParam(defaultValue = "100", required = false) String size
    ) {
        return new ResponseEntity(postService.readPostsByPage(postType, userId, beginDate, endDate, upKindCode
            , kindCode, sidoCode, gunguCode, genderType, neuterType, index, size), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> readPost(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(postService.readPost(postId), HttpStatus.OK);
    }

    @PostMapping(value = "/{postId}/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseType> setStatus(@PathVariable("postId") String postId,
                                             @RequestBody String userId,
                                             @RequestParam Post.StateType stateType) {
        return new ResponseEntity<>(postService.setState(postId, userId, stateType), HttpStatus.OK);
    }

    @PostMapping(value = "/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseType> deletePost(@PathVariable("postId") String postId, @RequestBody String userId) {
        return new ResponseEntity<>(postService.delete(postId, userId), HttpStatus.OK);
    }

    /*
        Comments
     */
    @PostMapping(value = "/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseType> createComment(@PathVariable("postId") String postId, @RequestBody CommentRequest commentRequest) {
        return new ResponseEntity<>(commentService.create(postId, commentRequest.getUserId(), commentRequest.getText()), HttpStatus.OK);
    }

    @PostMapping(value = "/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseType> deleteComment(@PathVariable("commentId") String commentId) {
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
    @PostMapping(value = "/{postId}/bookmarks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseType> registBookmark(@PathVariable("postId") String postId, @RequestBody String userId) {
        return new ResponseEntity(bookmarkService.registerBookmark(postId, userId), HttpStatus.OK);
    }

    @PostMapping(value = "/{postId}/bookmarks/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseType> cancelBookmark(@PathVariable("postId") String postId, @RequestBody String userId) {
        return new ResponseEntity(bookmarkService.cancelBookmark(postId, userId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/bookmarks")
    public ResponseEntity<PostPreviewDTO> readBookmarkByPostId(@PathVariable String postId,
                                                               @RequestParam(defaultValue = "0", required = false) String index,
                                                               @RequestParam(defaultValue = "100", required = false) String size
    ) {
        return new ResponseEntity(bookmarkService.readBookmarkByPostId(postId, index, size), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/bookmarks")
    public ResponseEntity<PostPreviewDTO> readBookmarkByUserId(@PathVariable String userId,
                                                               @RequestParam(defaultValue = "0", required = false) String index,
                                                               @RequestParam(defaultValue = "100", required = false) String size
    ) {
        return new ResponseEntity(postService.readBookmarkByUserId(userId, index, size), HttpStatus.OK);
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
