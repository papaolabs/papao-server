package com.papaolabs.api.domain.service;

import com.google.common.collect.Lists;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.QPost;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.interfaces.v1.controller.response.StatsDTO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class StatsServiceImpl implements StatsService {
    @NotNull
    private final PostRepository postRepository;
    private static final String DATE_FORMAT = "yyyyMMdd";

    public StatsServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public StatsDTO getTotalStats(String beginDate, String endDate) {
        if (isEmpty(beginDate)) {
            beginDate = getDefaultDate(DATE_FORMAT);
        }
        if (isEmpty(endDate)) {
            endDate = getDefaultDate(DATE_FORMAT);
        }
        QPost post = QPost.post;
        BooleanBuilder builder = new BooleanBuilder().and(post.noticeBeginDate.between(convertStringToDate(beginDate),
                                                                                  convertStringToDate(endDate)));
        List<Post> results = Lists.newArrayList(postRepository.findAll(builder));
        StatsDTO statsDTO = new StatsDTO();
        statsDTO.setTotalCount(results.size());
        statsDTO.setBeginDate(beginDate);
        statsDTO.setEndDate(endDate);
        statsDTO.setSaveCount(0);
        statsDTO.setAdoptionCount(0);
        statsDTO.setReturnCount(0);
        statsDTO.setEuthanasiaCount(0);
        statsDTO.setNaturalDeathCount(0);
        statsDTO.setUnknownCount(0);
        for (Post p : results) {
            switch (p.getStateType()) {
                case PROCESS:
                    statsDTO.setSaveCount(statsDTO.getSaveCount() + 1);
                    break;
                case RETURN:
                    statsDTO.setReturnCount(statsDTO.getReturnCount() + 1);
                    break;
                case NATURALDEATH:
                    statsDTO.setNaturalDeathCount(statsDTO.getNaturalDeathCount() + 1);
                    break;
                case EUTHANASIA:
                    statsDTO.setEuthanasiaCount(statsDTO.getEuthanasiaCount() + 1);
                    break;
                case ADOPTION:
                    statsDTO.setAdoptionCount(statsDTO.getAdoptionCount() + 1);
                    break;
                case UNKNOWN:
                    statsDTO.setUnknownCount(statsDTO.getUnknownCount() + 1);
                    break;
            }
        }
        return statsDTO;
    }

    private String getDefaultDate(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }

    private Date convertStringToDate(String from) {
        SimpleDateFormat transFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return transFormat.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
