package com.papaolabs.push.interfaces.v1;

import com.papaolabs.push.domain.model.PushRequest;
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
    public ResponseEntity sendPush(PushRequest pushRequest) {
        this.pushService.sendPush(pushRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/send/list")
    public ResponseEntity sendPush(@RequestBody List<PushRequest> pushRequests) {
        this.pushService.sendPush(pushRequests);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity ownPushList(@RequestParam String userId) {
        return new ResponseEntity(this.pushService.getOwnPushLogs(userId), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity deletePush(@RequestParam String pushId) {
        this.pushService.deletePushLog(pushId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
