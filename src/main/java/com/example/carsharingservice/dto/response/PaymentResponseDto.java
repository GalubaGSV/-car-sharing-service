package com.example.carsharingservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Schema
public class PaymentResponseDto {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "Paid")
    private String paymentStatus;
    @Schema(example = "Fine")
    private String paymentType;
    @Schema(example = "1")
    private Long rentalId;
    @Schema(example = "http://payments/success")
    private String paymentUrl;
    @Schema(example = "1")
    private String paymentSessionId;
    @Schema(example = "100")
    private BigDecimal paymentAmount;
}
