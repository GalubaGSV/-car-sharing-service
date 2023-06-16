package com.example.carsharingservice.stripepaymant;

import com.example.carsharingservice.model.Payment;

public interface StripePaymentProvider {
    void createPaymentSession(Payment payment);
}
