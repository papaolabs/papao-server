package com.papaolabs.openapi.interfaces.v1;

import com.papaolabs.openapi.domain.service.AccountKitService;
import com.papaolabs.client.account.dto.AccountKitAccessResponse;
import com.papaolabs.client.account.dto.AccountKitProfileResponse;
import com.papaolabs.client.account.dto.DeleteAccountResponseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/openapi/v1/account")
public class V1AccountKitController {
    @NotNull
    private final AccountKitService accountKitService;

    public V1AccountKitController(AccountKitService accountKitService) {
        this.accountKitService = accountKitService;
    }

    @GetMapping("/valid")
    public AccountKitAccessResponse validateAccount(@RequestParam String code) {
        return accountKitService.validateAuthorizationCode(code);
    }

    @GetMapping("/profile")
    public AccountKitProfileResponse getAccountProfile(@RequestParam String accessToken) {
        return accountKitService.getProfile(accessToken);
    }

    @GetMapping("/delete")
    public DeleteAccountResponseResponse deleteAccount(@RequestParam String accountId) {
        return accountKitService.removeAccount(accountId);
    }
}
