package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.UserService;
import com.papaolabs.api.infrastructure.feign.openapi.dto.PushTypeDTO;
import com.papaolabs.api.interfaces.v1.controller.request.JoinRequest;
import com.papaolabs.api.interfaces.v1.controller.request.PushRequest;
import com.papaolabs.api.interfaces.v1.controller.response.PushHistoryDTO;
import com.papaolabs.api.interfaces.v1.controller.response.ResponseType;
import com.papaolabs.api.interfaces.v1.controller.response.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
public class V1UserController {
    @NotNull
    private final UserService userService;

    public V1UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseType> join(@RequestBody @Valid JoinRequest joinRequest) {
        return new ResponseEntity(userService.join(joinRequest.getUserId(),
                                                   joinRequest.getUserToken(),
                                                   joinRequest.getPhone()), HttpStatus.OK);
    }

    @PostMapping(value = "/push", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseType> setPush(@RequestBody PushRequest pushRequest) {
        return new ResponseEntity(userService.setPush(pushRequest.getType(), pushRequest.getUserId(), pushRequest.getDeviceId()),
                                  HttpStatus.OK);
    }

    @PostMapping(value = "/profile")
    public ResponseEntity<UserDTO> profile(@RequestBody String userId) {
        return new ResponseEntity(userService.profile(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/nickname")
    public ResponseEntity<String> generateNickname() {
        return new ResponseEntity(userService.generateNickname(), HttpStatus.OK);
    }

    @PostMapping(value = "/push/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PushHistoryDTO> getPushHistory(@RequestBody String userId,
                                                         @RequestParam(defaultValue = "0", required = false) String index,
                                                         @RequestParam(defaultValue = "100", required = false) String size) {
        return new ResponseEntity<PushHistoryDTO>(userService.getPushHistory(userId, index, size), HttpStatus.OK);
    }

    @PostMapping(value = "/push/setting", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PushTypeDTO> getPushHistory(@RequestBody PushTypeDTO pushTypeDTO) {
        return new ResponseEntity<PushTypeDTO>(userService.setPushType(pushTypeDTO.getUserId(),
                                                                       pushTypeDTO.getDeviceId(),
                                                                       pushTypeDTO.getAlarmYn(),
                                                                       pushTypeDTO.getRescueAlarmYn(),
                                                                       pushTypeDTO.getPostAlarmYn()), HttpStatus.OK);
    }
}
