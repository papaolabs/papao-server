package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.dto.PushDTO;
import com.papaolabs.api.interfaces.v1.dto.JoinDTO;

public interface UserService {
    JoinDTO join(String userId, String userToken, String phone);

    PushDTO setPush(String uid, String deviceToken);
}
