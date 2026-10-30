package com.apextech.accountservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenAccountSessionResponse {
        private String accountNumber;
        private String accountType;
        private String currency;
        private String cif;
        private String branchCode;
}