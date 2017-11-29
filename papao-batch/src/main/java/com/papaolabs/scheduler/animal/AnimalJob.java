package com.papaolabs.scheduler.animal;

import com.papaolabs.batch.domain.service.PostService;
import com.papaolabs.scheduler.BatchType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Slf4j
@Component
public class AnimalJob {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    @NotNull
    private final PostService postService;

    public AnimalJob(PostService postService) {
        this.postService = postService;
    }

    @Scheduled(cron = "0 0 2 1 1/1 ?") // 매달 1일 02시에 실행
    public void year() {
        for (int i = 120; i >= 0; i--) { // 최근 10년간
            batch(BatchType.MONTH, i);
        }
    }

    @Scheduled(cron = "0 0/10 * 1/1 * ?") // 10분마다 한달치 실행
    public void month() {
        batch(BatchType.MONTH, 0);
    }

    @Scheduled(cron = "0 0/1 * 1/1 * ?") // 5분마다 당일치 실행
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
            endDate = endDate.minusDays(minus);
        } else {
            log.debug("Not valid BatchType !!");
        }
        log.info("[BATCH_START] type: {}, startDate : {}, endDate : {}", type, startDate.format(formatter), endDate.format(formatter));
        stopWatch.start();
        this.postService.syncPostList(startDate.format(formatter), endDate.format(formatter));
        stopWatch.stop();
        log.info("[BATCH_END} executionTime : {} millis", stopWatch.getLastTaskTimeMillis());
    }
}
