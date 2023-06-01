package com.example.carsharingservice.service.mapper;

import java.math.BigDecimal;
import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.PaymentStatus;
import com.example.carsharingservice.model.PaymentType;
import com.example.carsharingservice.model.Rental;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentMapperTest {

    private final PaymentMapper paymentMapper = new PaymentMapper();

    @Test
    public void testMapToModel() {
        // Arrange
        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setPaymentAmount(BigDecimal.valueOf(500));
        dto.setPaymentType(PaymentType.PAYMENT);
        dto.setPaymentStatus(PaymentStatus.PENDING);
        dto.setRentalId(1L);
        dto.setPaymentSessionId("session123");
        dto.setPaymentUrl("https://example.com/payment");

        // Act
        Payment payment = paymentMapper.mapToModel(dto);

        // Assert
        assertNotNull(payment);
        assertEquals(dto.getPaymentAmount(), payment.getPaymentAmount());
        assertEquals(PaymentType.PAYMENT, payment.getPaymentType());
        assertEquals(PaymentStatus.PENDING, payment.getPaymentStatus());
        assertNotNull(payment.getRental());
        assertEquals(dto.getRentalId(), payment.getRental().getId());
        assertEquals(dto.getPaymentSessionId(), payment.getPaymentSessionId());
        assertEquals(dto.getPaymentUrl(), payment.getPaymentUrl());
    }

    @Test
    public void testMapToDto() {
        // Arrange
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setPaymentAmount(BigDecimal.valueOf(750));
        payment.setPaymentType(PaymentType.PAYMENT);
        payment.setPaymentStatus(PaymentStatus.PAID);
        Rental rental = new Rental();
        rental.setId(1L);
        payment.setRental(rental);
        payment.setPaymentSessionId("session123");
        payment.setPaymentUrl("https://example.com/payment");

        // Act
        PaymentResponseDto dto = paymentMapper.mapToDto(payment);

        // Assert
        assertNotNull(dto);
        assertEquals(payment.getId(), dto.getId());
        assertEquals(payment.getPaymentAmount(), dto.getPaymentAmount());
        assertEquals(payment.getPaymentStatus().getValue(), dto.getPaymentStatus());
        assertEquals(payment.getPaymentType().getValue(), dto.getPaymentType());
        assertEquals(payment.getRental().getId(), dto.getRentalId());
        assertEquals(payment.getPaymentSessionId(), dto.getPaymentSessionId());
        assertEquals(payment.getPaymentUrl(), dto.getPaymentUrl());
    }
}
