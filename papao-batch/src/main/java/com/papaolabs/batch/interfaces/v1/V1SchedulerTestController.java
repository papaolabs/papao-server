package com.papaolabs.batch.interfaces.v1;

import com.papaolabs.batch.domain.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/test")
public class V1SchedulerTestController {
    @NotNull
    private final PostService postService;

    public V1SchedulerTestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public void sync() {
        this.postService.syncPostList("20171105", "20171105");
    }
}
