package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.request.StripeUserRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.model.Payment;
import java.util.List;
import java.util.stream.Collectors;
import com.example.carsharingservice.model.PaymentType;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.StripePaymentService;
import com.example.carsharingservice.service.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final DtoMapper<PaymentRequestDto, PaymentResponseDto, Payment> mapper;
    private final PaymentService paymentService;
    private final StripePaymentService stripePaymentService;

    @Value("${stripe.apikey}")
    private String stripeKey;

    @GetMapping
    public List<PaymentResponseDto> getUserPayments(@RequestParam(name = "user_id") Long userId) {
        return paymentService.findByRentalUserId(userId).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public PaymentResponseDto addPayment(@RequestBody StripeUserRequestDto stripeUserRequestDto) {
        Payment payment = new Payment();
        payment.setPaymentType(PaymentType.PAYMENT);
        Rental rental = new Rental();
        rental.setId(stripeUserRequestDto.getRentalId());
        payment.setRental(rental);
        stripePaymentService.createPaymentSession(payment);
        return mapper.mapToDto(paymentService.add(payment));
    }

    @GetMapping("/success")
    public RedirectView successfulStripePayments() {
        return new RedirectView("/success.html");
    }

    @GetMapping("/cancel")
    public RedirectView   returnPaymentPausedMessage() {
        return new RedirectView("/cancel.html");
    }
}
