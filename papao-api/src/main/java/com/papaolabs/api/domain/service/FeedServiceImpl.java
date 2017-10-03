package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.Feed;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.FeedRepository;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.AnimalApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FeedServiceImpl implements FeedService {
    @Value("${seoul.api.animal.appKey}")
    private String appKey;
    @NotNull
    private final AnimalApiClient animalApiClient;
    @NotNull
    private final FeedRepository feedRepository;

    public FeedServiceImpl(AnimalApiClient animalApiClient, FeedRepository feedRepository) {
        this.animalApiClient = animalApiClient;
        this.feedRepository = feedRepository;
    }

    @Override
    public Feed create(String noticeBeginDate,
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
                       String weight) {
        Feed feed = new Feed();
        feed.setNoticeBeginDate(convertStringToDate(noticeBeginDate));
        feed.setNoticeEndDate(convertStringToDate(noticeEndDate));
        feed.setImageUrl(imageUrl);
        feed.setThumbImageUrl(thumbImageUrl);
        feed.setState(state);
        feed.setGender(gender);
        feed.setNeuterYn(neuterYn);
        feed.setDescription(description);
        feed.setShelterName(shelterName);
        feed.setShelterAddress(shelterAddress);
        feed.setShelterTel(shelterTel);
        feed.setDepartment(department);
        feed.setManagerName(managerName);
        feed.setManagerTel(managerTel);
        feed.setNote(note);
        feed.setDesertionNo(desertionNo);
        feed.setHappenDate(convertStringToDate(happenDate));
        feed.setHappenPlace(convertStringToDate(happenPlace));
        feed.setKindCode(kindCode);
        feed.setColorCode(colorCode);
        feed.setAge(Integer.valueOf(age));
        feed.setWeight(Float.valueOf(weight));
        return feedRepository.save(feed);
    }

    @Override
    public Feed update(String id,
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
                       String weight) {
        Feed feed = new Feed();
        feed.setId(Long.valueOf(id));
        feed.setNoticeBeginDate(convertStringToDate(noticeBeginDate));
        feed.setNoticeEndDate(convertStringToDate(noticeEndDate));
        feed.setImageUrl(imageUrl);
        feed.setThumbImageUrl(thumbImageUrl);
        feed.setState(state);
        feed.setGender(gender);
        feed.setNeuterYn(neuterYn);
        feed.setDescription(description);
        feed.setShelterName(shelterName);
        feed.setShelterAddress(shelterAddress);
        feed.setShelterTel(shelterTel);
        feed.setDepartment(department);
        feed.setManagerName(managerName);
        feed.setManagerTel(managerTel);
        feed.setNote(note);
        feed.setDesertionNo(desertionNo);
        feed.setHappenDate(convertStringToDate(happenDate));
        feed.setHappenPlace(convertStringToDate(happenPlace));
        feed.setKindCode(kindCode);
        feed.setColorCode(colorCode);
        feed.setAge(Integer.valueOf(age));
        feed.setWeight(Float.valueOf(weight));
        return feedRepository.save(feed);
    }

    @Override
    public void delete(String id) {
        feedRepository.delete(Long.valueOf(id));
    }

    private Date convertStringToDate(String from) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return transFormat.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
