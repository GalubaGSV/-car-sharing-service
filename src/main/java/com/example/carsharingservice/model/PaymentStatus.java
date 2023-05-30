package com.example.carsharingservice.model;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("Pending"),
    PAID("Paid");
    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }
}
