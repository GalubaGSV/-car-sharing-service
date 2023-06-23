package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.request.RentalRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.dto.response.RentalResponseDto;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.stripepaymant.StripePaymentProvider;
import com.example.carsharingservice.service.UserService;
import com.example.carsharingservice.service.impl.TelegramNotificationService;
import com.example.carsharingservice.mapper.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class PaymentControllerTest {

    @Mock
    private DtoMapper<PaymentRequestDto, PaymentResponseDto, Payment> paymentMapper;
    @Mock
    private DtoMapper<RentalRequestDto, RentalResponseDto, Rental> rentalMapper;
    @Mock
    private PaymentService paymentService;
    @Mock
    private StripePaymentProvider stripePaymentProvider;
    @Mock
    private UserService userService;
    @Mock
    private RentalService rentalService;
    @Mock
    private TelegramNotificationService telegramNotificationService;
    @Mock
    private Authentication auth;
    @Mock
    private UserDetails userDetails;
    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserPayments_ManagerRole_ReturnsPaymentResponseDtos() {
        Pageable pageRequest = PageRequest.of(0, 20);
        Long userId = 1L;
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment());
        payments.add(new Payment());
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("manager@example.com");
        User user = new User();
        user.setRole(Role.MANAGER);
        when(userService.findByEmail(anyString())).thenReturn(java.util.Optional.of(user));
        when(paymentService.findByRentalUserId(userId, pageRequest)).thenReturn(payments);
        when(paymentMapper.mapToDto(any(Payment.class))).thenReturn(new PaymentResponseDto());
        List<PaymentResponseDto> result = paymentController.getUserPayments(userId, auth, 20, 0);
        assertEquals(payments.size(), result.size());
    }

    @Test
    public void testGetUserPayments_UserRole_ReturnsPaymentResponseDtos() {
        Pageable pageRequest = PageRequest.of(0, 20);
        Long userId = 1L;
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment());
        payments.add(new Payment());
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user@example.com");
        User user = new User();
        user.setRole(Role.CUSTOMER);
        user.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(java.util.Optional.of(user));
        when(paymentService.findByRentalUserId(userId, pageRequest)).thenReturn(payments);
        when(paymentMapper.mapToDto(any(Payment.class))).thenReturn(new PaymentResponseDto());
        List<PaymentResponseDto> result = paymentController.getUserPayments(userId, auth, 20, 0);
        assertEquals(payments.size(), result.size());
    }

    @Test
    public void testGetUserPayments_Unauthorized_ThrowsRuntimeException() {
        Long userId = 1L;

        when(auth.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("unauthorized@example.com");
        when(userService.findByEmail(anyString())).thenReturn(java.util.Optional.empty());
        assertThrows(RuntimeException.class, () -> paymentController.getUserPayments(userId, auth, 20, 0));
    }

    @Test
    public void testSuccessfulStripePayments_ReturnsSuccessPaymentString() {
        String result = paymentController.successfulStripePayments();
        assertEquals("Success payment", result);
    }

    @Test
    public void testReturnPaymentPausedMessage_ReturnsCancelPaymentString() {
        String result = paymentController.returnPaymentPausedMessage();
        assertEquals("Cancel payment", result);
    }
}
