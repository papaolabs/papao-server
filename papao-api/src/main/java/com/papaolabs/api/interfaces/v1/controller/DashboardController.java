package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.CommentService;
import com.papaolabs.api.domain.service.PostService;
import com.papaolabs.api.interfaces.v1.controller.response.PostDTO;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;

import static com.papaolabs.api.infrastructure.persistence.jpa.entity.QComment.comment;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @NotNull
    private final PostService postService;
    @NotNull
    private final CommentService commentService;

    public DashboardController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/posts/{postId}/share")
    public ModelAndView getDetailPage(@PathVariable("postId") String postId, HttpServletRequest request, ModelAndView model) {
        model.setViewName("pages/detail");
        model.addObject("post", postService.readPost(postId));
        model.addObject("comments", commentService.readComments(postId));
        return model;
    }
}