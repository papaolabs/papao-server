package com.papaolabs.api.infrastructure.persistence.scheduler;

import com.papaolabs.api.domain.model.Kind;
import com.papaolabs.api.domain.model.Post;
import com.papaolabs.api.domain.model.Shelter;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.KindRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.ShelterRepository;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.AnimalApiClient;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.AnimalApiResponse;
import com.papaolabs.api.interfaces.v1.dto.type.NeuterType;
import com.papaolabs.api.interfaces.v1.dto.type.PostType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isAllBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Component
public class PostJob {
    private static final String UNKNOWN = "UNKNOWN";
    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String MAX_SIZE = "500000";
    private static final String START_INDEX = "1";
    @Value("${seoul.api.animal.appKey}")
    private String appKey;
    @NotNull
    private final AnimalApiClient animalApiClient;
    @NotNull
    private final PostRepository postRepository;
    @NotNull
    private final KindRepository kindRepository;
    @NotNull
    private final ShelterRepository shelterRepository;

    public PostJob(AnimalApiClient animalApiClient,
                   PostRepository postRepository,
                   KindRepository kindRepository,
                   ShelterRepository shelterRepository) {
        this.animalApiClient = animalApiClient;
        this.postRepository = postRepository;
        this.kindRepository = kindRepository;
        this.shelterRepository = shelterRepository;
    }

    //    @Scheduled(cron = "0 0 2 1 1/1 ?") // 매달 1일 02시에 실행
    @Scheduled(fixedRate = 10000000L) // 매달 1일 02시에 실행
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
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
        posts(startDate.format(formatter), endDate.format(formatter));
        stopWatch.stop();
        log.info("[BATCH_END} executionTime : {} millis", stopWatch.getLastTaskTimeMillis());
    }

    public void posts(String beginDate, String endDate) {
        String beginDateParam = beginDate;
        String endDateParam = endDate;
        if (isEmpty(beginDate)) {
            beginDateParam = getDefaultDate(DATE_FORMAT);
        }
        if (isEmpty(endDate)) {
            endDateParam = getDefaultDate(DATE_FORMAT);
        }
        AnimalApiResponse response = animalApiClient.animal(appKey, beginDateParam, endDateParam, EMPTY, EMPTY,
                                                            EMPTY, EMPTY, EMPTY, EMPTY, START_INDEX, MAX_SIZE);
        if (response != null) {
            List<Post> posts = postRepository.findByHappenDateGreaterThanEqualAndHappenDateLessThanEqual(convertStringToDate(beginDate),
                                                                                                         convertStringToDate(endDate));
            List<AnimalApiResponse.Body.Items.AnimalItemDTO> animalItems = response.getBody()
                                                                                   .getItems()
                                                                                   .getItem();
            log.info("AnimalItemDTO, beginDate : {}, endDate : {}, size : {}", beginDate, endDate, animalItems.size());
            postRepository.save(animalItems.stream()
                                           .map(this::transform)
                                           .map(x -> {
                                               posts.forEach(y -> {
                                                   if (y.getDesertionId()
                                                        .equals(x.getDesertionId())) {
                                                       x.setId(y.getId());
                                                       x.setCreatedDate(y.getCreatedDate());
                                                   }
                                               });
                                               return x;
                                           })
                                           .collect(Collectors.toList()));
        } else {
            log.info("PostJob, post not found.. beginDate : {}, endDate : {}", beginDate, endDate);
        }
    }

    private String getDefaultDate(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }

    private Post transform(AnimalApiResponse.Body.Items.AnimalItemDTO animalItemDTO) {
        String kindName = convertKindName(animalItemDTO.getKindCd());
        Kind mockKind = new Kind();
        mockKind.setUpKindCode(-1L);
        mockKind.setKindCode(-1L);
        Kind kind = Optional.ofNullable(kindRepository.findByKindName(kindName))
                            .orElse(mockKind);
        String[] orgNames = animalItemDTO.getOrgNm()
                                         .split(SPACE);
        Shelter mockShelter = new Shelter();
        mockShelter.setCityCode(-1L);
        mockShelter.setCityName(UNKNOWN);
        mockShelter.setTownCode(-1L);
        mockShelter.setTownName(UNKNOWN);
        mockShelter.setShelterCode(-1L);
        mockShelter.setShelterName(UNKNOWN);
        Shelter shelter = new Shelter();
        switch (orgNames.length) {
            case 1:
                shelter = shelterRepository.findByCityName(orgNames[0])
                                           .stream()
                                           .findFirst()
                                           .orElse(mockShelter);
                break;
            case 2:
                shelter = shelterRepository.findByTownName(orgNames[1])
                                           .stream()
                                           .findFirst()
                                           .orElse(mockShelter);
                break;
        }
        if ("-1".equals(shelter.getTownCode())) {
            shelter = shelterRepository.findByShelterName(animalItemDTO.getCareNm())
                                       .stream()
                                       .findFirst()
                                       .orElse(mockShelter);
        }
        Post post = new Post();
        post.setDesertionId(Long.valueOf(animalItemDTO.getDesertionNo()));
        post.setImageUrl(animalItemDTO.getPopfile());
        post.setType(PostType.SYSTEM.getCode());
        post.setState(animalItemDTO.getProcessState());
        post.setGender(animalItemDTO.getSexCd());
        post.setNeuter(isEmpty(animalItemDTO.getNeuterYn()) ? NeuterType.U.name() : animalItemDTO.getNeuterYn());
        post.setUserId(PostType.SYSTEM.getCode());
        post.setUserName(animalItemDTO.getCareNm());
        post.setUserAddress(animalItemDTO.getCareAddr());
        post.setUserContact(animalItemDTO.getCareTel());
        post.setHappenDate(convertStringToDate(animalItemDTO.getHappenDt()));
        post.setHappenPlace(isNotEmpty(animalItemDTO.getOrgNm()) ? animalItemDTO.getOrgNm() : UNKNOWN);
        post.setUprCode(String.valueOf(shelter.getCityCode()));
        post.setOrgCode(String.valueOf(shelter.getTownCode()));
        post.setKindUpCode(String.valueOf(kind.getUpKindCode()));
        post.setKindCode(String.valueOf(kind.getKindCode()));
        try {
            post.setAge(Integer.valueOf(convertAge(animalItemDTO.getAge())));
        } catch (NumberFormatException nfe) {
            post.setAge(-1);
        }
        post.setWeight(Float.valueOf(convertWeight(animalItemDTO.getWeight())));
        post.setIntroduction(animalItemDTO.getNoticeComment());
        post.setFeature(animalItemDTO.getSpecialMark());
        post.setIsDisplay(TRUE);
        return post;
    }

    private String convertWeight(String weight) {
        if (isEmpty(weight)) {
            return "-1";
        }
        if (weight.contains("(Kg)")) {
            weight = weight.replace("(Kg)", "");
            try {
                Float.valueOf(weight);
            } catch (NumberFormatException nfe) {
                weight = "-1";
            }
        }
        return weight;
    }

    private String convertAge(String age) {
        String result = age.replace(" ", "");
        if (isEmpty(result) || isAllBlank(result)) {
            return "-1";
        }
        if (result.contains("(년생)")) {
            return result.replace("(년생)", "");
        }
        return result;
    }

    private String convertKindName(String kindName) {
        if (kindName.contains("[개] ")) {
            return kindName.replace("[개] ", "");
        }
        if (kindName.contains("[기타축종] ")) {
            return kindName.replace("[기타축종] ", "");
        }
        if (kindName.contains("[고양이]")) {
            return kindName.replace("[고양이]", "고양이");
        }
        return kindName;
    }

    private String convertDateToString(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat(DATE_FORMAT);
        return transFormat.format(date);
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
