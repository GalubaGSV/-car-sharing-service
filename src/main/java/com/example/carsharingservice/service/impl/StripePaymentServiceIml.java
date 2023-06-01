package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.config.StripeConfig;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.PaymentStatus;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.StripePaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StripePaymentServiceIml implements StripePaymentService {
    private static final String YOUR_DOMAIN = "http://localhost:4242";
    private final PaymentService paymentService;
    private final StripeConfig stripeConfig;

    public void createPaymentSession(Payment payment) {
        Stripe.apiKey = stripeConfig.getSecretKey();
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(YOUR_DOMAIN + "/success.html")
                        .setCancelUrl(YOUR_DOMAIN + "/cancel.html")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPrice(createPrice(payment))
                                        .build())
                        .build();
        Session session = null;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            throw new RuntimeException("Can't create payment connection ", e);
        }
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentAmount(BigDecimal.valueOf(session.getAmountTotal() / 100));
        payment.setPaymentSessionId(session.getId());
        payment.setPaymentUrl(session.getUrl());
    }

    private String createPrice(Payment payment) {
        Stripe.apiKey = stripeConfig.getSecretKey();
        Map<String, Object> params = new HashMap<>();
        params.put("unit_amount",
                paymentService.calculatePrice(payment)
                        .multiply(BigDecimal.valueOf(100))
                        .intValue());
        params.put("currency", "usd");
        params.put("product", "prod_NzsxSUBeOj0ICq");
        Price price = null;
        try {
            price = Price.create(params);
        } catch (StripeException e) {
            throw new RuntimeException("Can't create price ", e);
        }
        return price.getId();
    }
}
