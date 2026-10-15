package com.apextech.accountservice.service;

import com.apextech.accountservice.dto.request.OpenAccountRequest;
import com.apextech.accountservice.dto.response.OpenAccountResponse;
import com.apextech.accountservice.dto.response.SessionResponse;

public interface AccountService {

    SessionResponse openAccountValidate(OpenAccountRequest request);
    OpenAccountResponse openAccountCommit(String sessionId);

}
