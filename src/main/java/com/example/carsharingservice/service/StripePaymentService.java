package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Payment;

public interface StripePaymentService {
    void createPaymentSession(Payment payment);
}
