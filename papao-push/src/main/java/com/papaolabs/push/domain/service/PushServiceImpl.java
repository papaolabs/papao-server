package com.papaolabs.push.domain.service;

import com.papaolabs.client.PushClient;
import com.papaolabs.push.domain.model.PushRequest;
import com.papaolabs.push.infrastructure.persistence.jpa.entity.PushLog;
import com.papaolabs.push.infrastructure.persistence.jpa.entity.PushUser;
import com.papaolabs.push.infrastructure.persistence.jpa.repository.PushLogRepository;
import com.papaolabs.push.infrastructure.persistence.jpa.repository.PushUserRepository;
import com.papaolabs.push.interfaces.dto.PushHistory;
import com.papaolabs.push.interfaces.dto.PushTypeDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;

@Service
public class PushServiceImpl implements PushService {
    @NotNull
    private final PushClient pushClient;
    @NotNull
    private final PushLogRepository pushLogRepository;
    @NotNull
    private final PushUserRepository pushUserRepository;
    private Pattern p = Pattern.compile("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+");

    public PushServiceImpl(PushClient pushClient,
                           PushLogRepository pushLogRepository,
                           PushUserRepository pushUserRepository) {
        this.pushClient = pushClient;
        this.pushLogRepository = pushLogRepository;
        this.pushUserRepository = pushUserRepository;
    }

    @Override
    public void sendPush(PushRequest request, String postId) {
        List<PushUser> pushUsers = new ArrayList<>();
        if (request.getUserId()
                   .equals("-9999")) {
            pushUsers = pushUserRepository.findAll();
        } else {
            pushUsers = pushUserRepository.findByUserId(String.valueOf(request.getUserId()));
        }
        List<PushLog> pushLogs = new ArrayList<>();
        for (PushUser pushUser : pushUsers) {
            PushLog pushLog = new PushLog();
            pushLog.setType(PushLog.PushType.getType(request.getType()
                                                            .name()));
            pushLog.setUserId(pushUser.getUserId());
            pushLog.setPostId(StringUtils.isNotEmpty(postId) ? Long.valueOf(postId) : -1L);
            pushLog.setMessage(p.matcher(request.getMessage())
                                .replaceAll(" "));
            if (pushUser.getAlarmYn() != PushUser.YesNoType.N) {
                pushClient.send(pushUser.getDeviceId(),
                                request.getMessage(),
                                request.getType()
                                       .name(),
                                postId);
                pushLogs.add(pushLog);
            } else if (request.getType() == PushRequest.PushType.ALARM || request.getType() == PushRequest.PushType.SEARCH) {
                if (pushUser.getRescueAlarmYn() != PushUser.YesNoType.N) {
                    pushClient.send(pushUser.getDeviceId(),
                                    request.getMessage(),
                                    request.getType()
                                           .name(),
                                    postId);
                    pushLogs.add(pushLog);
                }
            } else if (request.getType() == PushRequest.PushType.POST) {
                if (pushUser.getPostAlarmYn() != PushUser.YesNoType.N) {
                    pushClient.send(pushUser.getDeviceId(),
                                    request.getMessage(),
                                    request.getType()
                                           .name(),
                                    postId);
                    pushLogs.add(pushLog);
                }
            }
        }
        pushLogRepository.save(pushLogs.stream()
                                       .filter(distinctByKey(PushLog::getUserId))
                                       .distinct()
                                       .collect(Collectors.toList()));
    }

    @Override
    public void sendPush(List<PushRequest> requests, String postId) {
        for (PushRequest req : requests) {
            this.sendPush(req, postId);
        }
    }

    @Override
    public PushHistory getOwnPushLogs(String userId, String index, String size) {
        PageRequest pageRequest = new PageRequest(Integer.valueOf(index),
                                                  Integer.valueOf(size),
                                                  new Sort(Sort.Direction.DESC, "id"));
        Page<PushLog> pushLogs = pushLogRepository.findByUserIdOrderByIdDesc(userId, pageRequest);
        PushHistory pushHistory = new PushHistory();
        pushHistory.setUserId(userId);
        pushHistory.setTotalElements(pushLogs.getTotalElements());
        pushHistory.setTotalPages(pushLogs.getTotalPages());
        pushHistory.setPushLogs(pushLogs.getContent()
                                        .stream()
                                        .map(x -> {
                                            PushHistory.PushLog pushLog = new PushHistory.PushLog();
                                            pushLog.setId(x.getId());
                                            pushLog.setType(PushHistory.PushLog.PushType.getType(x.getType()
                                                                                                  .name()));
                                            pushLog.setPostId(x.getPostId());
                                            pushLog.setMessage(x.getMessage());
                                            pushLog.setCreatedDate(x.getCreatedDateTime()
                                                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                            pushLog.setUpdatedDate(x.getLastModifiedDateTime()
                                                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                            return pushLog;
                                        })
                                        .collect(Collectors.toList()));
        return pushHistory;
    }

    @Override
    public void deletePushLog(String pushId) {
        pushLogRepository.delete(Long.valueOf(pushId));
    }

    @Override
    public PushTypeDTO setPushType(String userId, String deviceId, String alarmYn, String rescueAlarmYn, String postAlarmYn) {
        PushUser pushUsers = pushUserRepository.findByDeviceId(deviceId);
        pushUsers.setAlarmYn(PushUser.YesNoType.valueOf(alarmYn));
        pushUsers.setRescueAlarmYn(PushUser.YesNoType.valueOf(rescueAlarmYn));
        pushUsers.setPostAlarmYn(PushUser.YesNoType.valueOf(postAlarmYn));
        pushUserRepository.save(pushUsers);
        PushTypeDTO pushTypeDTO = new PushTypeDTO();
        pushTypeDTO.setUserId(pushUsers.getUserId());
        pushTypeDTO.setDeviceId(pushUsers.getDeviceId());
        pushTypeDTO.setAlarmYn(pushUsers.getAlarmYn()
                                        .name());
        pushTypeDTO.setRescueAlarmYn(pushUsers.getRescueAlarmYn()
                                              .name());
        pushTypeDTO.setPostAlarmYn(pushUsers.getPostAlarmYn()
                                            .name());
        return pushTypeDTO;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), TRUE) == null;
    }
}
