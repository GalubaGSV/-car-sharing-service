package com.example.carsharingservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class StripeUserRequestDto {
    @Schema(example = "1")
    private Long rentalId;
    @Schema(example = "Payment")
    private String paymentType;
}
