package com.papaolabs.push.domain.service;

import com.papaolabs.push.domain.model.PushRequest;
import com.papaolabs.push.interfaces.dto.PushHistory;

import java.util.List;

public interface PushService {
    void sendPush(PushRequest request);

    void sendPush(List<PushRequest> requests);

    PushHistory getOwnPushLogs(String userId);

    void deletePushLog(String pushId);
}
