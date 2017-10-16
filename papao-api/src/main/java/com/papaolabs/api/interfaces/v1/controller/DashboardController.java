package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.CommentService;
import com.papaolabs.api.domain.service.PostService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.EMPTY;

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

    @GetMapping
    public ModelAndView main(HttpServletRequest request, ModelAndView model) {
        String beginDate = Optional.ofNullable(request.getParameter("beginDate")).orElse("");
        String endDate = Optional.ofNullable(request.getParameter("endDate")).orElse("");
        model.setViewName("pages/index");
        model.addObject("posts", postService.readPosts(beginDate, endDate, EMPTY, EMPTY, EMPTY));
        return model;
    }

    @GetMapping("/detail")
    public ModelAndView getDetailPage(HttpServletRequest request, ModelAndView model) {
        String postId = request.getParameter("pageId");
        model.setViewName("pages/detail");
        model.addObject("post", postService.readPost(postId));
        model.addObject("comments", commentService.readComments(postId));
        return model;
    }

    @PostMapping("/comment")
    public ModelAndView addComment(HttpServletRequest request, ModelAndView model) {
        String postId = request.getParameter("postId");
        String text = request.getParameter("text");
        commentService.createByGuest(postId, text);
        model.setViewName("pages/detail");
        model.addObject("post", postService.readPost(postId));
        model.addObject("comments", commentService.readComments(postId));
        return model;
    }
}
