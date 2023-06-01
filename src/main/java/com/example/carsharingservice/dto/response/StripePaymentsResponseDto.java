package com.example.carsharingservice.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class StripePaymentsResponseDto {
    private String id;
    private BigDecimal amount;
    private String status;
}
