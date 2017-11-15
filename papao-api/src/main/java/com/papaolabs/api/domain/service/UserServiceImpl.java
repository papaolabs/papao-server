package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.dto.PushDTO;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.PushUser;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.User;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PushUserRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.UserRepository;
import com.papaolabs.api.interfaces.v1.dto.JoinDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PushUserRepository pushUserRepository;

    public UserServiceImpl(UserRepository userRepository,
                           PushUserRepository pushUserRepository) {
        this.userRepository = userRepository;
        this.pushUserRepository = pushUserRepository;
    }

    @Override
    public JoinDTO join(String userId, String userToken, String phone) {
        User user = new User();
        user.setUid(userId);
        user.setPhone(phone);
        user.setNickName("");
        user.setEmail(StringUtils.EMPTY);
        User result = userRepository.save(user);
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setId(result.getUid());
        joinDTO.setNickName(result.getNickName());
        joinDTO.setPhone(result.getPhone());
        joinDTO.setPush(FALSE);
        return joinDTO;
    }

    @Override
    public PushDTO setPush(String userId, String deviceId) {
        PushUser pushUser = new PushUser();
        pushUser.setUserId(Long.valueOf(userId));
        pushUser.setDeviceId(deviceId);
        pushUserRepository.save(pushUser);
        PushDTO pushDTO = new PushDTO();
        pushDTO.setUserId(userId);
        pushDTO.setDeviceIds(pushUserRepository.findByUserId(Long.valueOf(userId))
                                               .stream()
                                               .map(PushUser::getDeviceId)
                                               .collect(Collectors.toList()));
        return pushDTO;
    }
}
