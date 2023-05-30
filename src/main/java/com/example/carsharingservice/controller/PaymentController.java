package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.StripeUserRequestDto;
import com.example.carsharingservice.model.Payment;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Value("${stripe.apikey}")
    private String stripeKey;

    @GetMapping("/?user_id={userId}")
    public List<Payment> getUserPayments(@RequestParam Long userId) {
        /**
         * todo add logic
         */

        return Collections.emptyList();
    }

    @PostMapping
    public StripeUserRequestDto addPayment(@RequestBody StripeUserRequestDto stripeUser) {
        /**
         * todo add logic
         * todo  return DTO instead void
         * todo  param DTO instead  long
         */
        Stripe.apiKey = stripeKey;
        Map<String, Object> automaticPaymentMethods =
                new HashMap<>();
        automaticPaymentMethods.put("enabled", true);
        Map<String, Object> params = new HashMap<>();
        params.put("amount", 2000);
        params.put("currency", "usd");
        params.put(
                "automatic_payment_methods",
                automaticPaymentMethods
        );
        PaymentIntent paymentIntent = null;
        try {
            paymentIntent =
                    PaymentIntent.create(params);
        } catch (StripeException e) {
            throw new RuntimeException("Something wrong " + e);
        }
        stripeUser.setStripeCustomerId(paymentIntent.getId());
        return stripeUser;
    }



    @GetMapping("/success")
    public void checkSuccessfulStripePayments() {
        /**
         * todo add logic
         */
        return;
    }

    @GetMapping("/cancel")
    public void returnPaymentPausedMessage() {
        /**
         * todo add logic
         */
        return;
    }

}
