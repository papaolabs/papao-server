package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.CommentService;
import com.papaolabs.api.domain.service.PostService;
import com.papaolabs.api.domain.service.AccountKitService;
import com.papaolabs.api.infrastructure.persistence.restapi.account.dto.AccountKitAccessResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.account.dto.AccountKitProfileResponse;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @NotNull
    private final PostService postService;
    @NotNull
    private final CommentService commentService;
    @NotNull
    private final AccountKitService accountKitService;

    public DashboardController(PostService postService, CommentService commentService, AccountKitService accountKit) {
        this.postService = postService;
        this.commentService = commentService;
        this.accountKitService = accountKit;
    }

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request, ModelAndView model) {
        model.setViewName("pages/login");
        return model;
    }

    @GetMapping("/success")
    public ModelAndView validateAccountKit(HttpServletRequest request, ModelAndView model) {
        String authCode = request.getParameter("code");
        try {
            AccountKitAccessResponse token = accountKitService.validateAuthorizationCode(authCode);
            if (token != null) {
                AccountKitProfileResponse profile = accountKitService.getProfile(token.getAccessToken());
                // save(token); -
                model.setViewName("pages/success");
                model.addObject("profile", profile);
                return model.addObject("status", Response.ok().build());
            }
        } catch (IOException e) {
            return model.addObject(Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
        }
        return model.addObject(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    @GetMapping
    public ModelAndView main(HttpServletRequest request, ModelAndView model) {
        String endDate = Optional.ofNullable(request.getParameter("endDate"))
                .orElse("");
        String upKindCode = Optional.ofNullable(request.getParameter("upKindCode"))
                .orElse(EMPTY);
        upKindCode = upKindCode.equals("0") ? "" : upKindCode;
        String upKindName = Optional.ofNullable(request.getParameter("upKindName"))
                .orElse(EMPTY);
        String cityCode = Optional.ofNullable(request.getParameter("cityCode"))
                .orElse(EMPTY);
        cityCode = cityCode.equals("0") ? "" : cityCode;
        String cityName = Optional.ofNullable(request.getParameter("cityName"))
                .orElse(EMPTY);
        model.setViewName("pages/index");
        model.addObject("posts", postService.readPosts(EMPTY, getDefaultDate(endDate), upKindCode, cityCode, EMPTY, "0", "100"));
        model.addObject("endDate", isEmpty(endDate) ? "0" : endDate);
        model.addObject("upKindCode", upKindCode);
        model.addObject("upKindName", upKindName);
        model.addObject("cityCode", cityCode);
        model.addObject("cityName", cityName);
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
