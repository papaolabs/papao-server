package com.papaolabs.push.domain.service;

import com.papaolabs.client.PushClient;
import com.papaolabs.push.domain.model.PushRequest;
import com.papaolabs.push.infrastructure.persistence.jpa.entity.PushLog;
import com.papaolabs.push.infrastructure.persistence.jpa.entity.PushUser;
import com.papaolabs.push.infrastructure.persistence.jpa.repository.PushLogRepository;
import com.papaolabs.push.infrastructure.persistence.jpa.repository.PushUserRepository;
import com.papaolabs.push.interfaces.dto.PushHistory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PushServiceImpl implements PushService {
    @NotNull
    private final PushClient pushClient;
    @NotNull
    private final PushLogRepository pushLogRepository;
    @NotNull
    private final PushUserRepository pushUserRepository;

    public PushServiceImpl(PushClient pushClient,
                           PushLogRepository pushLogRepository,
                           PushUserRepository pushUserRepository) {
        this.pushClient = pushClient;
        this.pushLogRepository = pushLogRepository;
        this.pushUserRepository = pushUserRepository;
    }

    @Override
    public void sendPush(PushRequest request, String postId) {
        List<PushUser> pushUsers = pushUserRepository.findByUserId(request.getUserId());
        List<PushLog> pushLogs = new ArrayList<>();
        for(PushUser pushUser : pushUsers) {
            PushLog pushLog = new PushLog();
            pushLog.setUserId(pushUser.getUserId());
            pushLog.setPostId(StringUtils.isNotEmpty(postId) ? Long.valueOf(postId) : -1L);
            pushLog.setMessage(request.getMessage());
            pushClient.send(pushUser.getDeviceId(), request.getMessage());
            pushLogs.add(pushLog);
        }
        pushLogRepository.save(pushLogs);
    }

    @Override
    public void sendPush(List<PushRequest> requests, String postId) {
        for (PushRequest req : requests) {
            this.sendPush(req, postId);
        }
    }

    @Override
    public PushHistory getOwnPushLogs(String userId) {
        PushHistory pushHistory = new PushHistory();
        pushHistory.setUserId(Long.valueOf(userId));
        pushHistory.setPushLogs(pushLogRepository.findByUserId(Long.valueOf(userId))
                                                 .stream()
                                                 .map(x -> {
                                                     PushHistory.PushLog pushLog = new PushHistory.PushLog();
                                                     pushLog.setId(x.getId());
                                                     pushLog.setPostId(x.getPostId());
                                                     pushLog.setMessage(x.getMessage());
                                                     pushLog.setCreatedDate(x.getCreatedDateTime()
                                                                             .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                                     pushLog.setUpdatedDate(x.getLastModifiedDateTime()
                                                                             .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                                     return pushLog;
                                                 })
                                                 .sorted(Comparator.comparing(PushHistory.PushLog::getCreatedDate))
                                                 .collect(Collectors.toList()));
        return pushHistory;
    }

    @Override
    public void deletePushLog(String pushId) {
        pushLogRepository.delete(Long.valueOf(pushId));
    }
}
