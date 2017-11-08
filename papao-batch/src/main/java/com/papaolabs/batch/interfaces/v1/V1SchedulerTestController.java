package com.papaolabs.batch.interfaces.v1;

import com.papaolabs.batch.domain.service.PostService;
import com.papaolabs.batch.infrastructure.jpa.entity.Post;
import com.papaolabs.batch.infrastructure.jpa.repository.PostRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/test")
public class V1SchedulerTestController {
    @NotNull
    private final PostService postService;
    @NotNull
    private final PostRepository repository;

    public V1SchedulerTestController(PostService postService,
                                     PostRepository repository) {
        this.postService = postService;
        this.repository = repository;
    }

    @GetMapping
    public void sync(@RequestParam String beginDate, @RequestParam String endDate) {
        this.postService.syncPostList(beginDate, endDate);
    }

    @GetMapping("/list")
    public List<Post> read() {
        return this.repository.findAll();
    }
}
