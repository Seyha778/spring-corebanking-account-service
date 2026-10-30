package com.apextech.accountservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OpenAccountRequest {

    @JsonProperty("cif")
    @NotBlank
    private String cif;

    @JsonProperty("branch_code")
    @NotBlank
    private String branchCode;

    @JsonProperty("account_type")
    private String accountType;

    @JsonProperty("currency")
    @NotBlank
    private String currency;

}
