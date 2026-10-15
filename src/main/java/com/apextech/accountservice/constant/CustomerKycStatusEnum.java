package com.apextech.accountservice.constant;

public enum CustomerKycStatusEnum {
    FULL("FULL"),
    HALF("HALF");

    final String value;

    CustomerKycStatusEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
