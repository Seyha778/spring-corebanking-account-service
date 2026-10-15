package com.apextech.accountservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class SessionResponse {

    private String sessionId;

    private long expiresIn;
}