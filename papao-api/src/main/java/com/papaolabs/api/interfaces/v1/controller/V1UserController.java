package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.UserService;
import com.papaolabs.api.interfaces.v1.controller.response.JoinDTO;
import com.papaolabs.api.interfaces.v1.controller.request.JoinRequest;
import com.papaolabs.api.interfaces.v1.controller.response.PushDTO;
import com.papaolabs.api.interfaces.v1.controller.request.PushRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<JoinDTO> join(@RequestBody JoinRequest joinRequest) {
        return new ResponseEntity(userService.join(joinRequest.getUserId(),
                                                   joinRequest.getUserToken(),
                                                   joinRequest.getUserToken()), HttpStatus.OK);
    }

    @PostMapping(value = "/push", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PushDTO> setPush(@RequestBody PushRequest pushRequest) {
        return new ResponseEntity(userService.setPush(pushRequest.getUserId(), pushRequest.getDeviceId()), HttpStatus.OK);
    }
}
