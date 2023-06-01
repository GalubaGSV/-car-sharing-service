package com.example.carsharingservice.dto.request;

import lombok.Data;

@Data
public class StripeUserRequestDto {
    private Long rentalId;
    private String paymentType;

}
