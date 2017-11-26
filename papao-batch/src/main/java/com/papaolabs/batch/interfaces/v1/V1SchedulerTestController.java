package com.papaolabs.batch.interfaces.v1;

import com.papaolabs.batch.domain.service.PostService;
import com.papaolabs.batch.infrastructure.jpa.repository.PostRepository;
import com.papaolabs.scheduler.animal.AnimalJob;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/sync")
public class V1SchedulerTestController {
    @NotNull
    private final PostService postService;
    @NotNull
    private final PostRepository repository;
    @NotNull
    private final AnimalJob animalJob;

    public V1SchedulerTestController(PostService postService,
                                     PostRepository repository, AnimalJob animalJob) {
        this.postService = postService;
        this.repository = repository;
        this.animalJob = animalJob;
    }

    @GetMapping("/all")
    public void syncByAll() {
        animalJob.year();
    }

    @GetMapping("/month")
    public void syncByMonth() {
        animalJob.month();
    }

    @GetMapping("/day")
    public void syncByDay() {
        animalJob.day();
    }
}
