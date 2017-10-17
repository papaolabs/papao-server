package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.CommentService;
import com.papaolabs.api.domain.service.PostService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Controller
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
        String endDate = Optional.ofNullable(request.getParameter("endDate"))
                                 .orElse("");
        String upKindCode = Optional.ofNullable(request.getParameter("upKindCode"))
                                    .orElse(EMPTY);
        String cityCode = Optional.ofNullable(request.getParameter("cityCode"))
                                  .orElse(EMPTY);
        model.setViewName("pages/index");
        model.addObject("posts", postService.readPosts(EMPTY, getDefaultDate(endDate), upKindCode, cityCode, EMPTY));
        model.addObject("endDate", endDate);
        model.addObject("upKindCode", upKindCode);
        model.addObject("cityCode", cityCode);
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
    public String addComment(HttpServletRequest request) {
        String postId = request.getParameter("postId");
        String text = request.getParameter("text");
        commentService.createByGuest(postId, text);
        return "redirect:/dashboard/detail?pageId=" + postId;
    }

    private String getDefaultDate(String minusDay) {
        Integer val = 0;
        if (isNotEmpty(minusDay)) {
            val = Integer.valueOf(minusDay);
        }
        LocalDateTime now = LocalDateTime.now()
                                         .minusDays(val);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return now.format(formatter);
    }
}
