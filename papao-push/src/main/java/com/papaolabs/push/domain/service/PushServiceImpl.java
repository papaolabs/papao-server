package com.papaolabs.push.domain.service;

import com.papaolabs.client.PushClient;
import com.papaolabs.push.domain.model.PushRequest;
import com.papaolabs.push.domain.model.PushResult;
import com.papaolabs.push.infrastructure.persistence.jpa.entity.PushLog;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class PushServiceImpl implements PushService {
    @NotNull
    private PushClient pushClient;

    public PushServiceImpl(PushClient pushClient) {
        this.pushClient = pushClient;
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
