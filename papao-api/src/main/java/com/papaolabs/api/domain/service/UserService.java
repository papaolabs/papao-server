package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.feign.openapi.dto.PushTypeDTO;
import com.papaolabs.api.interfaces.v1.controller.response.JoinDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PushDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PushHistoryDTO;
import com.papaolabs.api.interfaces.v1.controller.response.UserDTO;

public interface UserService {
    JoinDTO join(String userId, String userToken, String phone);

    PushDTO setPush(String type, String uid, String deviceToken);

    UserDTO profile(String uid);

    String generateNickname();

    PushHistoryDTO getPushHistory(String userId, String index, String size);

    PushTypeDTO setPushType(String userId, String deviceId, String alarmYn, String rescueAlarmYn, String postAlarmYn);
}
