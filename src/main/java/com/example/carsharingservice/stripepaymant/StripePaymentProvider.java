package com.example.carsharingservice.stripepaymant;

import com.example.carsharingservice.dto.response.StripePaymentSessionResponseDto;
import com.example.carsharingservice.model.Payment;

public interface StripePaymentProvider {
    StripePaymentSessionResponseDto createPaymentSession(Payment payment);
}
