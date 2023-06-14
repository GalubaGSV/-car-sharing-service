package com.example.carsharingservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Schema
public class StripePaymentsResponseDto {
    @Schema(example = "1")
    private String id;
    @Schema(example = "100")
    private BigDecimal amount;
    @Schema(example = "Paid")
    private String status;
}
