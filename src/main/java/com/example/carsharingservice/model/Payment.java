package com.example.carsharingservice.model;

import java.math.BigDecimal;

public class Payment {
    private Long id;
    private PaymentStatus paymentStatus;
    private PaymentType paymentType;
    private int rentalId;
    private String paymentUrl;
    private String paymentSessionId;
    private BigDecimal paymentAmount;
}
