package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.feign.openapi.PushApiClient;
import com.papaolabs.api.infrastructure.feign.openapi.dto.PushTypeDTO;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Breed;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.PushUser;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.User;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BreedRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PushUserRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.UserRepository;
import com.papaolabs.api.interfaces.v1.controller.response.JoinDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PushDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PushHistoryDTO;
import com.papaolabs.api.interfaces.v1.controller.response.ResponseType;
import com.papaolabs.api.interfaces.v1.controller.response.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;

@Service
public class UserServiceImpl implements UserService {
    @Value("classpath:adjective.txt")
    private Resource resource;
    private final UserRepository userRepository;
    private final PushUserRepository pushUserRepository;
    private final BreedRepository breedRepository;
    private final static String[] imageList = {"http://220.230.121.76:8000/v1/download/86d5b5dca78b4d908f7032df35d53c9e.png",
                                               "http://220.230.121.76:8000/v1/download/f7cdf7a82056462383f4a4e96651f9dd.png",
                                               "http://220.230.121.76:8000/v1/download/2d0e043d0fae488da5cec48c68ce71c0.png"};
    private final PushApiClient pushApiClient;

    public UserServiceImpl(UserRepository userRepository,
                           PushUserRepository pushUserRepository,
                           BreedRepository breedRepository, PushApiClient pushApiClient) {
        this.userRepository = userRepository;
        this.pushUserRepository = pushUserRepository;
        this.breedRepository = breedRepository;
        this.pushApiClient = pushApiClient;
    }

    @Override
    public ResponseType join(String userId, String userToken, String phone) {
        User userByUid = userRepository.findByUid(userId);
        if (userByUid != null) {
            return ResponseType.builder()
                               .code(ResponseType.ResponseCode.DUPLICATED.getCode())
                               .name(ResponseType.ResponseCode.DUPLICATED.name())
                               .build();
        }
        User user = new User();
        user.setUid(userId);
        user.setPhone(phone);
        Random random = new Random();
        int n = random.nextInt(imageList.length);
        user.setProfileUrl(imageList[n]);
        user.setNickName(generateNickname());
        user.setEmail(StringUtils.EMPTY);
        User result = userRepository.save(user);
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setId(result.getUid());
        joinDTO.setNickName(result.getNickName());
        joinDTO.setPhone(result.getPhone());
        joinDTO.setPush(FALSE);
        return ResponseType.builder()
                           .code(ResponseType.ResponseCode.SUCCESS.getCode())
                           .name(ResponseType.ResponseCode.SUCCESS.name())
                           .build();
    }

    @Override
    public ResponseType setPush(String type, String userId, String deviceId) {
        PushUser pushUser = pushUserRepository.findByDeviceId(deviceId);
        if (pushUser != null) {
            return ResponseType.builder()
                               .code(ResponseType.ResponseCode.DUPLICATED.getCode())
                               .name(ResponseType.ResponseCode.DUPLICATED.name())
                               .build();
        }
        PushUser.UserType userType = PushUser.UserType.getType(type);
        pushUser = new PushUser();
        pushUser.setType(userType);
        pushUser.setUserId(PushUser.UserType.GUEST == userType ? "-1" : userId);
        pushUser.setDeviceId(deviceId);
        pushUserRepository.save(pushUser);
        PushDTO pushDTO = new PushDTO();
        pushDTO.setType(pushUser.getType()
                                .name());
        pushDTO.setUserId(String.valueOf(pushUser.getUserId()));
        pushDTO.setDeviceIds(pushUserRepository.findByUserId(userId)
                                               .stream()
                                               .map(PushUser::getDeviceId)
                                               .collect(Collectors.toList()));
        return ResponseType.builder()
                           .code(ResponseType.ResponseCode.SUCCESS.getCode())
                           .name(ResponseType.ResponseCode.SUCCESS.name())
                           .build();
    }

    @Override
    public UserDTO profile(String uid) {
        User user = userRepository.findByUid(uid);
        if ("-1".equals(uid) || user == null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId("-1");
            return userDTO;
        }
        List<PushUser> pushUsers = pushUserRepository.findByUserId(uid);
        if (pushUsers == null || pushUsers.size() <= 0) {
            pushUsers = new ArrayList<>();
        }
        List<PushUser> pushUserList = pushUserRepository.findByUserId(user.getUid());
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUid());
        userDTO.setNickname(user.getNickName());
        userDTO.setPhone(user.getPhone());
        userDTO.setProfileUrl(user.getProfileUrl());
        userDTO.setDevicesToken(pushUserList
                                    .stream()
                                    .map(PushUser::getDeviceId)
                                    .collect(Collectors.toList()));
        return userDTO;
    }

    @Override
    public String generateNickname() {
        StringBuilder textBuilder = new StringBuilder();
        try {
            try (Reader reader = new BufferedReader(new InputStreamReader
                                                        (resource.getInputStream(), Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] adjectives = StringUtils.split(textBuilder.toString(), ",");
        Random random = new Random();
        int n = random.nextInt(adjectives.length);
        List<String> list = breedRepository.findAll()
                                           .stream()
                                           .map(Breed::getKindName)
                                           .collect(Collectors.toList());
        return StringUtils.join(adjectives[n], StringUtils.SPACE, StringUtils.deleteWhitespace(list.get(random.nextInt(list.size()))));
    }

    @Override
    public PushHistoryDTO getPushHistory(String userId, String index, String size) {
        return pushApiClient.ownPushList(userId, index, size);
    }

    @Override
    public PushTypeDTO setPushType(String userId, String deviceId, String alarmYn, String rescueAlarmYn, String postAlarmYn) {
        return pushApiClient.setPushType(userId, deviceId, alarmYn, rescueAlarmYn, postAlarmYn);
    }
}
