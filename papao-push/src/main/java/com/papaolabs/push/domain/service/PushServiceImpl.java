package com.papaolabs.push.domain.service;

import com.papaolabs.client.PushClient;
import com.papaolabs.push.domain.model.PushRequest;
import com.papaolabs.push.infrastructure.persistence.jpa.entity.PushLog;
import com.papaolabs.push.infrastructure.persistence.jpa.entity.PushUser;
import com.papaolabs.push.infrastructure.persistence.jpa.repository.PushLogRepository;
import com.papaolabs.push.infrastructure.persistence.jpa.repository.PushUserRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

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
    public void sendPush(PushRequest request) {
        PushUser pushUser = pushUserRepository.findByUserId(request.getUserId());
        String deviceId = pushUser.getDeviceId();
        PushLog pushLog = new PushLog();
        pushLog.setUserId(pushUser.getUserId());
        pushLog.setMessage(request.getMessage());
        pushClient.send(deviceId, request.getMessage());
        pushLogRepository.save(pushLog);
    }

    @Override
    public void sendPush(List<PushRequest> requests) {
        for (PushRequest req : requests) {
            this.sendPush(req);
        }
    }

    @Override
    public List<PushLog> getOwnPushLogs(String userId) {
        List<PushLog> pushLogs = pushLogRepository.findByUserId(Long.valueOf(userId));
        return pushLogs;
    }

    @Override
    public void deletePushLog(String pushId) {
        pushLogRepository.delete(Long.valueOf(pushId));
    }
}
