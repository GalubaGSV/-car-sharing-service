package com.example.carsharingservice.model;

public enum Role {
    MANAGER("Manager"),
    CUSTOMER("Customer");
    private final String value;

    Role(String value) {
        this.value = value;
    }
}
