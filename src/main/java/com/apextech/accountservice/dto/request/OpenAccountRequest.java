package com.apextech.accountservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OpenAccountRequest {

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("cif")
    private String cif;

    @JsonProperty("branch_code")
    private String branchCode;

    @JsonProperty("account_type")
    private String accountType;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("status")
    private String status;

    @JsonProperty("created_on")
    private OffsetDateTime createdOn;

}
