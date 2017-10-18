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
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private static final String MAX_SIZE = "100000";
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

/*
    @Scheduled(fixedRate = 100000000L)
    public void batch() {
        StopWatch stopWatch = new StopWatch();
        Integer yearDays = 365;
        LocalDateTime now;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        for (int i = 0; i < yearDays; i++) {
            now = LocalDateTime.now().minusYears(1)
                               .minusDays(i);
            stopWatch.start();
            posts(now.format(formatter), now.format(formatter));
            stopWatch.stop();
            log.info("[batchNo-{}] yyyyMMdd : {}, executionTime : {}",
                     i,
                     now.format(formatter),
                     TimeUnit.MILLISECONDS.toSeconds(stopWatch.getLastTaskTimeMillis()) + "s");
        }
    }
*/

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
            postRepository.save(response.getBody()
                                        .getItems()
                                        .getItem()
                                        .stream()
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
            log.debug("PostJob, post not found.. beginDate : {}, endDate : {}", beginDate, endDate);
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
        try{
            post.setAge(Integer.valueOf(convertAge(animalItemDTO.getAge())));
        }
        catch (NumberFormatException nfe) {
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
