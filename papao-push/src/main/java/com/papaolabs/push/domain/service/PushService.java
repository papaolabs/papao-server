package com.papaolabs.push.domain.service;

import com.papaolabs.push.domain.model.PushRequest;
import com.papaolabs.push.interfaces.dto.PushHistory;
import com.papaolabs.push.interfaces.dto.PushTypeDTO;

import java.util.List;

public interface PushService {
    void sendPush(PushRequest request, String postId);

    void sendPush(List<PushRequest> requests, String postId);

    PushHistory getOwnPushLogs(String userId, String index, String size);

    void deletePushLog(String pushId);

    PushTypeDTO setPushType(String userId, String deviceId, String alarmYn, String rescueAlarmYn, String postAlarmYn);
}
