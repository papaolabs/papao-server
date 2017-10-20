package com.papaolabs.api.infrastructure.persistence.scheduler;

import com.papaolabs.api.domain.service.PostService;
import com.papaolabs.api.domain.service.VisionService;
import com.papaolabs.api.interfaces.v1.dto.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static org.apache.commons.lang.StringUtils.EMPTY;

@Slf4j
@Component
public class PostJob {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    @NotNull
    private final VisionService visionService;
    @NotNull
    private final PostService postService;

    public PostJob(VisionService visionService, PostService postService) {
        this.visionService = visionService;
        this.postService = postService;
    }

    public void image() throws Exception {
        StopWatch stopWatch = new StopWatch();
        log.debug("[GOOGLE_VISION_START]");
        String baseDate = LocalDateTime.now()
                                       .minusDays(1)
                                       .format(formatter);
        stopWatch.start();
        List<PostDTO> posts = postService.readPosts(baseDate, baseDate, EMPTY, EMPTY, EMPTY, "1", "1");
        visionService.syncVisionData(posts);
        stopWatch.stop();
        log.debug("[GOOGLE_VISION_COMPLETE] executionTime : {} millis", stopWatch.getLastTaskTimeMillis());
    }

    @Scheduled(cron = "0 0 2 1 1/1 ?") // 매달 1일 02시에 실행
    public void year() {
        for (int i = 0; i < 10; i++) { // 최근 9년간
            batch(BatchType.YEAR, i);
        }
    }

    @Scheduled(cron = "0 0 0/6 1/1 * ?") // 6시간마다 실행
    public void month() {
        batch(BatchType.MONTH, 0);
    }

    @Scheduled(cron = "0 0/30 * 1/1 * ?") // 30분마다 실행
    public void day() {
        batch(BatchType.DAY, 0);
    }

    public void batch(BatchType type, Integer minus) {
        StopWatch stopWatch = new StopWatch();
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();
        if (BatchType.YEAR == type) {
            startDate = startDate.with(TemporalAdjusters.firstDayOfYear())
                                 .minusYears(minus);
            endDate = endDate.with(TemporalAdjusters.lastDayOfYear())
                             .minusYears(minus);
        } else if (BatchType.MONTH == type) {
            startDate = startDate.with(TemporalAdjusters.firstDayOfMonth())
                                 .minusMonths(minus);
            endDate = endDate.with(TemporalAdjusters.lastDayOfMonth())
                             .minusMonths(minus);
        } else if (BatchType.DAY == type) {
            startDate = startDate.minusDays(minus);
            endDate = startDate.minusDays(minus);
        } else {
            log.debug("Not valid BatchType !!");
        }
        log.info("[BATCH_START] type: {}, startDate : {}, endDate : {}", type, startDate.format(formatter), endDate.format(formatter));
        stopWatch.start();
        postService.syncPosts(startDate.format(formatter), endDate.format(formatter));
        stopWatch.stop();
        log.info("[BATCH_END} executionTime : {} millis", stopWatch.getLastTaskTimeMillis());
    }
}
