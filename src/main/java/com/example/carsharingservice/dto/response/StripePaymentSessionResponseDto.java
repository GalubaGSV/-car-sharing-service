package com.example.carsharingservice.dto.response;

import lombok.Data;

@Data
public class StripePaymentSessionResponseDto {
    private String id;
    private String url;
}
