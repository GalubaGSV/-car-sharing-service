package com.example.carsharingservice.model;

import lombok.Getter;

@Getter
public enum PaymentType {
    PAYMENT("Payment"),
    FINE("Fine");
    private final String value;

    PaymentType(String value) {
        this.value = value;
    }
}
