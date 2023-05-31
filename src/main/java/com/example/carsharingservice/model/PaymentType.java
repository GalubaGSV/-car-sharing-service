package com.example.carsharingservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum PaymentType {
    PAYMENT("Payment"),
    FINE("Fine");
    private final String value;

    PaymentType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PaymentType fromValue(String value) {
        for (PaymentType type : PaymentType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid PaymentType value: " + value);
    }
}
