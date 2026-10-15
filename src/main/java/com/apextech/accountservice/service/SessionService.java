package com.apextech.accountservice.service;

import com.apextech.accountservice.dto.response.SessionData;
import com.apextech.accountservice.dto.response.SessionResponse;


public interface SessionService {

    SessionResponse create(SessionData data);

    boolean verify(String sessionId);

    boolean consume(String sessionId);

    SessionData getSession(String sessionId);

}