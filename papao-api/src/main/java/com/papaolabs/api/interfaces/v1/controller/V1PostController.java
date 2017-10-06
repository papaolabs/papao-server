package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.AnimalService;
import com.papaolabs.api.domain.service.PostService;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class V1PostController {
    @NotNull
    private final AnimalService animalService;
    @NotNull
    private final PostService postService;

    @PostMapping
    public ResponseEntity create(@RequestParam(required = false) String noticeBeginDate,
                                 @RequestParam(required = false) String noticeEndDate,
                                 @RequestParam(required = false) String imageUrl,
                                 @RequestParam(required = false) String thumbImageUrl,
                                 @RequestParam(required = false) String state,
                                 @RequestParam(required = false) String gender,
                                 @RequestParam(required = false) String neuterYn,
                                 @RequestParam(required = false) String description,
                                 @RequestParam(required = false) String shelterName,
                                 @RequestParam(required = false) String shelterTel,
                                 @RequestParam(required = false) String shelterAddress,
                                 @RequestParam(required = false) String department,
                                 @RequestParam(required = false) String managerName,
                                 @RequestParam(required = false) String managerTel,
                                 @RequestParam(required = false) String note,
                                 @RequestParam(required = false) String desertionNo,
                                 @RequestParam(required = false) String happenDate,
                                 @RequestParam(required = false) String happenPlace,
                                 @RequestParam(required = false) String kindCode,
                                 @RequestParam(required = false) String colorCode,
                                 @RequestParam(required = false) String age,
                                 @RequestParam(required = false) String weight
    ) {
        return new ResponseEntity<>(postService.create(
            noticeBeginDate,
            noticeEndDate,
            imageUrl,
            thumbImageUrl,
            state,
            gender,
            neuterYn,
            description,
            shelterName,
            shelterTel,
            shelterAddress,
            department,
            managerName,
            managerTel,
            note,
            desertionNo,
            happenDate,
            happenPlace,
            kindCode,
            colorCode,
            age,
            weight
        ), HttpStatus.OK);
    }

    @GetMapping
    public List<PostDTO> read(@RequestParam(required = false) String beginDate,
                              @RequestParam(required = false) String endDate,
                              @RequestParam(required = false) String upKindCode,
                              @RequestParam(required = false) String uprCode,
                              @RequestParam(required = false) String orgCode
    ) {
        return postService.readPosts(beginDate, endDate, upKindCode, uprCode, orgCode);
    }
}
