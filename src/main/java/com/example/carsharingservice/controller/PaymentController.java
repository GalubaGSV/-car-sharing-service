package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.request.RentalRequestDto;
import com.example.carsharingservice.dto.request.StripeUserRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.dto.response.RentalResponseDto;
import com.example.carsharingservice.model.Payment;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.example.carsharingservice.model.PaymentType;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.StripePaymentService;
import com.example.carsharingservice.service.UserService;
import com.example.carsharingservice.service.impl.TelegramNotificationService;
import com.example.carsharingservice.service.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
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
    private final DtoMapper<RentalRequestDto, RentalResponseDto, Rental> rentalMapper;
    private final PaymentService paymentService;
    private final StripePaymentService stripePaymentService;
    private final UserService userService;
    private final RentalService rentalService;
    private final TelegramNotificationService telegramNotificationService;

    @GetMapping
    public List<PaymentResponseDto> getUserPayments(@RequestParam(name = "user_id") Long userId,
                                                    Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        User user = userService.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email " + email + " not found"));
        if (user.getRole().equals(Role.MANAGER)) {
            return paymentService.findByRentalUserId(userId).stream()
                    .map(mapper::mapToDto)
                    .collect(Collectors.toList());
        }
        if (Objects.equals(user.getId(), userId)) {
            return paymentService.findByRentalUserId(user.getId()).stream()
                    .map(mapper::mapToDto)
                    .collect(Collectors.toList());
        }
        throw new RuntimeException("You don't have permission get all payment by id: " + userId);

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
    public RedirectView   returnPaymentPausedMessage() {
        return new RedirectView("/cancel.html");
    }
}
