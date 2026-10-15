package com.apextech.accountservice.controller;

import com.apextech.accountservice.dto.request.OpenAccountRequest;
import com.apextech.accountservice.dto.response.OpenAccountResponse;
import com.apextech.accountservice.dto.response.ResponseMessage;
import com.apextech.accountservice.dto.response.ResponseMessageBuilder;
import com.apextech.accountservice.dto.response.SessionResponse;
import com.apextech.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/openValidate")
    public ResponseMessage<SessionResponse> openAccountValidate(@RequestBody OpenAccountRequest request) {

        SessionResponse response = accountService.openAccountValidate(request);

        return new ResponseMessageBuilder<SessionResponse>().success().data(response).build();
    }

    @PostMapping("/openCommit")
    public ResponseMessage<OpenAccountResponse> openAccountCommit(@RequestBody String sessionId) {

        OpenAccountResponse response = accountService.openAccountCommit(sessionId);

        return new ResponseMessageBuilder<OpenAccountResponse>().success().data(response).build();
    }
}
