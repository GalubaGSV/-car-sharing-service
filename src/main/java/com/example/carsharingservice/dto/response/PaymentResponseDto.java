package com.example.carsharingservice.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentResponseDto {
    private Long id;
    private String paymentStatus;
    private String paymentType;
    private Long rentalId;
    private String paymentUrl;
    private String paymentSessionId;
    private BigDecimal paymentAmount;
}
