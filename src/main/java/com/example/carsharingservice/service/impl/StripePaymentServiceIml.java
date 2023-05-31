package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.PaymentStatus;
import com.example.carsharingservice.service.StripePaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentServiceIml implements StripePaymentService {
    private static final String YOUR_DOMAIN = "http://localhost:4242";
    @Value("${stripe.apikey}")
    private String stripeKey;

    public void createPaymentSession(Payment payment) {
        Stripe.apiKey = stripeKey;
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(YOUR_DOMAIN + "/success.html")
                        .setCancelUrl(YOUR_DOMAIN + "/cancel.html")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPrice("price_1NDtBGDk0xvnjfubHRroVFXE")
                                        .build())
                        .build();
        Session session = null;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentAmount(BigDecimal.valueOf(session.getAmountTotal() / 100));
        payment.setPaymentSessionId(session.getId());
        payment.setPaymentUrl(session.getUrl());
    }
}
