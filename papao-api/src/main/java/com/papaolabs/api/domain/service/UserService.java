package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.controller.response.PushDTO;
import com.papaolabs.api.interfaces.v1.controller.response.JoinDTO;

public interface UserService {
    JoinDTO join(String userId, String userToken, String phone);

    PushDTO setPush(String type, String uid, String deviceToken);

    String generateNickname();
}
