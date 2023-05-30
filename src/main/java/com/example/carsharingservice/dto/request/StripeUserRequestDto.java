package com.example.carsharingservice.dto.request;

import lombok.Data;

@Data
public class StripeUserRequestDto {
    private String amount;
    private String currency;
    private String status;

    private String stripeCustomerId;
}
