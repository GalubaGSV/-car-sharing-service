package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.model.PaymentStatus;
import com.example.carsharingservice.model.PaymentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequestDto {
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private Long rentalId;
    private String paymentUrl;
    private String paymentSessionId;
    private BigDecimal paymentAmount;
}
