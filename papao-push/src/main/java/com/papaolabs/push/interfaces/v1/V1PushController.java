package com.papaolabs.push.interfaces.v1;

import com.papaolabs.push.domain.model.PushRequest;
import com.papaolabs.push.interfaces.dto.PushTypeDTO;
import com.papaolabs.push.domain.service.PushService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1/push")
public class V1PushController {
    @NotNull
    private final PushService pushService;

    public V1PushController(PushService pushService) {
        this.pushService = pushService;
    }

    @PostMapping("/send")
    public ResponseEntity sendPush(@RequestBody PushRequest pushRequest, @RequestParam(required = false) String postId) {
        this.pushService.sendPush(pushRequest, postId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/send/list")
    public ResponseEntity sendPush(@RequestBody List<PushRequest> pushRequests, @RequestParam(required = false) String postId) {
        this.pushService.sendPush(pushRequests, postId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/setting")
    public ResponseEntity setPushType(@RequestBody PushTypeDTO pushTypeRequest) {
        return new ResponseEntity(this.pushService.setPushType(pushTypeRequest.getUserId(),
                                                               pushTypeRequest.getDeviceId(),
                                                               pushTypeRequest.getAlarmYn(),
                                                               pushTypeRequest.getRescueAlarmYn(),
                                                               pushTypeRequest.getPostAlarmYn()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity ownPushList(@RequestParam String userId, @RequestParam String index, @RequestParam String size) {
        return new ResponseEntity(this.pushService.getOwnPushLogs(userId, index, size), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity deletePush(@RequestParam String pushId) {
        this.pushService.deletePushLog(pushId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
