package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.CommentService;
import com.papaolabs.api.domain.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
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
    private final ConnectionRepository connectionRepository;

    public DashboardController(PostService postService, CommentService commentService, ConnectionRepository connectionRepository) {
        this.postService = postService;
        this.commentService = commentService;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping("/login")
    public ModelAndView login (HttpServletRequest request, ModelAndView model){
        model.setViewName("pages/login");
        return model;
    }

    @GetMapping("/success")
    public ModelAndView success (HttpServletRequest request, ModelAndView model){
        model.addObject("status", request.getParameter("status"));
        model.addObject("code", request.getParameter("code"));
        model.addObject("state", request.getParameter("state"));
        User user = getConnect();
        model.setViewName("pages/success");
        return model;
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

    private User getConnect() {
        Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
        if (connection == null) {
            return null;
        }
        ConnectionData data = connection.createData();
        return new User(data.getProviderUserId(), data.getDisplayName());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        String providerUserId;
        String displayName;
    }
}
