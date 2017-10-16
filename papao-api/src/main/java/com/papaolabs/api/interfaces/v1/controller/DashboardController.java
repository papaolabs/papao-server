package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.PostService;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @NotNull
    private final PostService postService;

    public DashboardController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ModelAndView main(HttpServletRequest request, ModelAndView model) {
        model.setViewName("pages/index");
        model.addObject("posts", postService.readPosts(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));
        return model;
    }

    @GetMapping("/detail")
    public ModelAndView getDetailPage(HttpServletRequest request, ModelAndView model) {
        model.setViewName("pages/detail");
        PostDTO postDTO = postService.readPost(request.getParameter("pageId"));
        System.out.println(postDTO.getId());
        model.addObject("post", postDTO);
        return model;
    }
}
