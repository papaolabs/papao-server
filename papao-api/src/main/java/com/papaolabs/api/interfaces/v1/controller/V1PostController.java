package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.PostService;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
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

    public V1PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity create(@RequestParam(required = false) String happenDate,
                                 @RequestParam(required = false) String happenPlace,
                                 @RequestParam(required = false) String uprCode,
                                 @RequestParam(required = false) String orgCode,
                                 @RequestParam(required = false) String uid,
                                 @RequestParam(required = false) String contacts,
                                 @RequestParam(required = false) String postType,
                                 @RequestParam(required = false) String imageUrl,
                                 @RequestParam(required = false) String gender,
                                 @RequestParam(required = false) String neuter,
                                 @RequestParam(required = false) String kindUpCode,
                                 @RequestParam(required = false) String kindCode,
                                 @RequestParam(required = false) String age,
                                 @RequestParam(required = false) String weight,
                                 @RequestParam(required = false) String introduction,
                                 @RequestParam(required = false) String feature
    ) {
        return new ResponseEntity<>(postService.create(
            imageUrl,
            postType,
            gender,
            neuter,
            uid,
            contacts,
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
    public ResponseEntity read(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(postService.readPost(postId), HttpStatus.OK);
    }
}
