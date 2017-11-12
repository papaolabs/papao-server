package com.papaolabs.push.domain.service;

import com.papaolabs.push.domain.model.PushRequest;
import com.papaolabs.push.domain.model.PushResult;
import com.papaolabs.push.infrastructure.persistence.jpa.entity.PushLog;

import java.util.List;

public interface PushService {
    PushResult sendPush(PushRequest request);

    PushResult sendPush(List<PushRequest> requests);

    List<PushLog> getOwnPushLogs(String userId);

    void deletePushLog(String userId, String pushId);
}
