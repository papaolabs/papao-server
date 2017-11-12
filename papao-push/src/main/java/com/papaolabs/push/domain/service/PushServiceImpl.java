package com.papaolabs.push.domain.service;

import com.papaolabs.client.PushClient;
import com.papaolabs.push.domain.model.PushRequest;
import com.papaolabs.push.domain.model.PushResult;
import com.papaolabs.push.infrastructure.persistence.jpa.entity.PushLog;
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
    public PushResult sendPush(PushRequest request) {
        return null;
    }

    @Override
    public PushResult sendPush(List<PushRequest> requests) {
        return null;
    }

    @Override
    public List<PushLog> getOwnPushLogs(String userId) {
        return null;
    }

    @Override
    public void deletePushLog(String userId, String pushId) {
    }
}
