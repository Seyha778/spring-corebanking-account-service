package com.apextech.accountservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OpenAccountResponse{
        private String accountNumber;
        private String accountType;
        private String currency;
}