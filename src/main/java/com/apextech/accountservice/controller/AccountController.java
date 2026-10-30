package com.apextech.accountservice.controller;

import com.apextech.accountservice.dto.request.OpenAccountRequest;
import com.apextech.accountservice.dto.request.SessionRequest;
import com.apextech.accountservice.dto.response.OpenAccountResponse;
import com.apextech.accountservice.dto.response.ResponseMessage;
import com.apextech.accountservice.dto.response.ResponseMessageBuilder;
import com.apextech.accountservice.dto.response.SessionResponse;
import com.apextech.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/openValidate")
    public ResponseMessage<SessionResponse> openAccountValidate(@RequestBody @Valid OpenAccountRequest request) {
        if(!StringUtils.hasText(request.getAccountType())){
            request.setAccountType("SAVING");
        }
        SessionResponse response = accountService.openAccountValidate(request);

        return new ResponseMessageBuilder<SessionResponse>().success().data(response).build();
    }

    @PostMapping("/openCommit")
    public ResponseMessage<OpenAccountResponse> openAccountCommit(@RequestBody @Valid SessionRequest request) {

        OpenAccountResponse response = accountService.openAccountCommit(request);

        return new ResponseMessageBuilder<OpenAccountResponse>().success().data(response).build();
    }

    @PostMapping("/openCurrentValidate")
    public ResponseMessage<SessionResponse> openCurrentAccountValidate(@RequestBody @Valid OpenAccountRequest request) {
        if(!StringUtils.hasText(request.getAccountType())){
            request.setAccountType("CURRENT");
        }
        SessionResponse response = accountService.openAccountValidate(request);

        return new ResponseMessageBuilder<SessionResponse>().success().data(response).build();
    }

    @PostMapping("/openCurrentCommit")
    public ResponseMessage<OpenAccountResponse> openCurrentAccountCommit(@RequestBody  @Valid SessionRequest request) {

        OpenAccountResponse response = accountService.openAccountCommit(request);

        return new ResponseMessageBuilder<OpenAccountResponse>().success().data(response).build();
    }
}
