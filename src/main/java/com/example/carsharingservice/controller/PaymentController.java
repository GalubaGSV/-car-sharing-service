package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.request.StripeUserRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.dto.response.StripePaymentsResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Payment;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.carsharingservice.model.PaymentStatus;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.mapper.DtoMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentIntentCollection;
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
    private static final int LIMIT_PAYMENTS = 100;
    private final DtoMapper<PaymentRequestDto, PaymentResponseDto, Payment> mapper;
    private final PaymentService paymentService;

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
    public PaymentResponseDto addPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        Stripe.apiKey = stripeKey;
        Map<String, Object> automaticPaymentMethods =
                new HashMap<>();
        automaticPaymentMethods.put("enabled", true);
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentRequestDto.getPaymentAmount());
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
        paymentRequestDto.setPaymentStatus(PaymentStatus.PENDING);
        paymentRequestDto.setPaymentSessionId(paymentIntent.getId());

        // todo figure out how get url
        paymentRequestDto.setPaymentUrl("URL");

        Payment payment = paymentService.add(mapper.mapToModel(paymentRequestDto));
        return mapper.mapToDto(payment);
    }



    @GetMapping("/success")
    public List<StripePaymentsResponseDto> checkSuccessfulStripePayments() {
        /**
         * todo add logic
         */
        Stripe.apiKey = stripeKey;
        Map<String, Object> params = new HashMap<>();
        params.put("limit", LIMIT_PAYMENTS);
        PaymentIntentCollection paymentIntents = null;
        try {
            paymentIntents = PaymentIntent.list(params);
        } catch (StripeException e) {
            throw new RuntimeException("Something wrong " + e);
        }
        List<StripePaymentsResponseDto> successPayments = new ArrayList<>();
        StripePaymentsResponseDto stripePaymentsResponseDto = new StripePaymentsResponseDto();
        paymentIntents.autoPagingIterable().forEach(paymentIntent -> {
            stripePaymentsResponseDto.setId(paymentIntent.getId());
            stripePaymentsResponseDto.setAmount(BigDecimal.valueOf(paymentIntent.getAmount()));
            stripePaymentsResponseDto.setStatus(paymentIntent.getStatus());
            successPayments.add(stripePaymentsResponseDto);
        });
        return successPayments;
    }

    @GetMapping("/cancel")
    public void returnPaymentPausedMessage() {
        /**
         * todo add logic
         */
        return;
    }

}
