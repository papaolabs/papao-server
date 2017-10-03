package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.AnimalService;
import com.papaolabs.api.domain.service.FeedService;
import com.papaolabs.api.interfaces.v1.dto.FeedDTO;
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
@RequestMapping("/v1/feeds/")
@RequiredArgsConstructor
public class V1FeedController {
    @NotNull
    private final AnimalService animalService;
    @NotNull
    private final FeedService feedService;

    @PostMapping
    public ResponseEntity create(@RequestParam String noticeBeginDate,
                                 @RequestParam String noticeEndDate,
                                 @RequestParam String imageUrl,
                                 @RequestParam String thumbImageUrl,
                                 @RequestParam String state,
                                 @RequestParam String gender,
                                 @RequestParam String neuterYn,
                                 @RequestParam String description,
                                 @RequestParam String shelterName,
                                 @RequestParam String shelterTel,
                                 @RequestParam String shelterAddress,
                                 @RequestParam String department,
                                 @RequestParam String managerName,
                                 @RequestParam String managerTel,
                                 @RequestParam String note,
                                 @RequestParam String desertionNo,
                                 @RequestParam String happenDate,
                                 @RequestParam String happenPlace,
                                 @RequestParam String kindCode,
                                 @RequestParam String colorCode,
                                 @RequestParam String age,
                                 @RequestParam String weight
    ) {
        return new ResponseEntity<>(feedService.create(
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
    public List<FeedDTO> read(@RequestParam(required = false) String beginDate,
                              @RequestParam(required = false) String endDate,
                              @RequestParam(required = false) String upKindCode,
                              @RequestParam(required = false) String kindCode,
                              @RequestParam(required = false) String uprCode,
                              @RequestParam(required = false) String orgCode,
                              @RequestParam(required = false) String shelterCode,
                              @RequestParam(required = false) String state,
                              @RequestParam(defaultValue = "1") String pageNo,
                              @RequestParam(defaultValue = "100000") String numOfRows
    ) {
        return animalService.getAnimalList(beginDate, endDate, upKindCode, kindCode, uprCode, orgCode,
                                           shelterCode, state, pageNo, numOfRows);
    }
}
