package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.request.StripeUserRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.PaymentType;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.StripePaymentService;
import com.example.carsharingservice.service.UserService;
import com.example.carsharingservice.service.impl.TelegramNotificationService;
import com.example.carsharingservice.service.mapper.DtoMapper;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserService userService;
    private final TelegramNotificationService telegramNotificationService;

    @GetMapping
    public List<PaymentResponseDto> getUserPayments(
            @RequestParam(name = "user_id") Long userId,
            Authentication auth,
            @RequestParam(defaultValue = "20") Integer count,
            @RequestParam(defaultValue = "0") Integer page) {
        Pageable pageRequest = PageRequest.of(page, count);
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        User user = userService.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email " + email + " not found"));
        if (user.getRole().equals(Role.MANAGER)) {
            return paymentService.findByRentalUserId(userId, pageRequest).stream()
                    .map(mapper::mapToDto)
                    .collect(Collectors.toList());
        }
        if (Objects.equals(user.getId(), userId)) {
            return paymentService.findByRentalUserId(user.getId(), pageRequest).stream()
                    .map(mapper::mapToDto)
                    .collect(Collectors.toList());
        }
        throw new RuntimeException("You don't have permission "
                + "to get all payments by id: " + userId);
    }

    @PostMapping
    public PaymentResponseDto addPayment(@RequestBody StripeUserRequestDto stripeUserRequestDto) {
        Payment payment = new Payment();
        payment.setPaymentType(PaymentType.PAYMENT);
        Rental rental = new Rental();
        rental.setId(stripeUserRequestDto.getRentalId());
        payment.setRental(rental);
        stripePaymentService.createPaymentSession(payment);

        payment = paymentService.add(payment);

        telegramNotificationService.sendMessage(String
                .format("New payment was created. \n"
                        + "Payment info: %s \n", payment
                ));

        return mapper.mapToDto(paymentService.add(payment));
    }

    @GetMapping("/success")
    public RedirectView successfulStripePayments() {
        return new RedirectView("/success.html");
    }

    @GetMapping("/cancel")
    public RedirectView returnPaymentPausedMessage() {
        return new RedirectView("/cancel.html");
    }
}
