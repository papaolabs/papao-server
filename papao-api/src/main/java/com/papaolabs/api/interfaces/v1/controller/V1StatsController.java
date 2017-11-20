package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.PostService;
import com.papaolabs.api.domain.service.StatsService;
import com.papaolabs.api.interfaces.v1.controller.response.PostRankingDTO;
import com.papaolabs.api.interfaces.v1.controller.response.StatsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/stats")
public class V1StatsController {
    @NotNull
    private final StatsService statsService;
    @NotNull
    private final PostService postService;

    public V1StatsController(StatsService statsService, PostService postService) {
        this.statsService = statsService;
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<StatsDTO> stats(@RequestParam(required = false) String beginDate,
                                          @RequestParam(required = false) String endDate) {
        return new ResponseEntity(this.statsService.getTotalStats(beginDate, endDate), HttpStatus.OK);
    }

    @GetMapping(value = "/posts")
    public ResponseEntity<PostRankingDTO> postRanking(@RequestParam(required = false) String beginDate,
                                                      @RequestParam(required = false) String endDate) {
        return new ResponseEntity(this.postService.readPostRanking(beginDate, endDate), HttpStatus.OK);
    }
}
