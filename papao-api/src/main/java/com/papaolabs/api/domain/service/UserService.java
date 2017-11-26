package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.feign.openapi.dto.PushTypeDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PushHistoryDTO;
import com.papaolabs.api.interfaces.v1.controller.response.ResponseType;
import com.papaolabs.api.interfaces.v1.controller.response.UserDTO;

public interface UserService {
    ResponseType join(String userId, String userToken, String phone);

    ResponseType setPush(String type, String uid, String deviceToken);

    UserDTO profile(String uid);

    String generateNickname();

    PushHistoryDTO getPushHistory(String userId, String index, String size);

    PushTypeDTO setPushType(String userId, String deviceId, String alarmYn, String rescueAlarmYn, String postAlarmYn);
}
