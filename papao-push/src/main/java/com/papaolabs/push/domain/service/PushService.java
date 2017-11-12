package com.papaolabs.push.domain.service;

import com.papaolabs.push.domain.model.PushRequest;
import com.papaolabs.push.infrastructure.persistence.jpa.entity.PushLog;

import java.util.List;

public interface PushService {
    void sendPush(PushRequest request);

    void sendPush(List<PushRequest> requests);

    List<PushLog> getOwnPushLogs(String userId);

    void deletePushLog(String pushId);
}
