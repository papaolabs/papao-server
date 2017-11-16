package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Breed;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.PushUser;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.User;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BreedRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PushUserRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.UserRepository;
import com.papaolabs.api.interfaces.v1.controller.response.JoinDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PushDTO;
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

    public UserServiceImpl(UserRepository userRepository,
                           PushUserRepository pushUserRepository,
                           BreedRepository breedRepository) {
        this.userRepository = userRepository;
        this.pushUserRepository = pushUserRepository;
        this.breedRepository = breedRepository;
    }

    @Override
    public JoinDTO join(String userId, String userToken, String phone) {
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
        User user = new User();
        user.setUid(userId);
        user.setPhone(phone);
        String nickName = adjectives[n] + list.get(random.nextInt(list.size()));
        user.setNickName(nickName);
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
