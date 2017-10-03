package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.Feed;

public interface FeedService {
    Feed create(String noticeBeginDate,
                String noticeEndDate,
                String imageUrl,
                String thumbImageUrl,
                String state,
                String gender,
                String neuterYn,
                String description,
                String shelterName,
                String shelterTel,
                String shelterAddress,
                String department,
                String managerName,
                String managerTel,
                String note,
                String desertionNo,
                String happenDate,
                String happenPlace,
                String kindCode,
                String colorCode,
                String age,
                String weight
    );

    Feed update(String id,
                String noticeBeginDate,
                String noticeEndDate,
                String imageUrl,
                String thumbImageUrl,
                String state,
                String gender,
                String neuterYn,
                String description,
                String shelterName,
                String shelterTel,
                String shelterAddress,
                String department,
                String managerName,
                String managerTel,
                String note,
                String desertionNo,
                String happenDate,
                String happenPlace,
                String kindCode,
                String colorCode,
                String age,
                String weight
    );

    void delete(String id);
}
