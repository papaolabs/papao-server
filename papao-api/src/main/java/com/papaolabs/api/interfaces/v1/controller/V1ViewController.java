package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.PostService;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/view")
public class V1ViewController {
    @NotNull
    private final PostService postService;

    public V1ViewController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/share")
    public ModelAndView getDetailPage(HttpServletRequest request, ModelAndView model) {
        System.out.println("/share/pages/detail");
        model.setViewName("pages/detail");
        PostDTO postDTO = postService.readPost(request.getParameter("pageId"));
        System.out.println(postDTO.getId());
        model.addObject("post", postDTO);
        return model;
    }
}
