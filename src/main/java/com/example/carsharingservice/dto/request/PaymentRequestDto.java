package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.model.Payment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Schema
public class PaymentRequestDto {
    @Enumerated(EnumType.STRING)
    @Schema(example = "Pending")
    private Payment.PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    @Schema(example = "Payment")
    private Payment.PaymentType paymentType;
    @Schema(example = "1")
    private Long rentalId;
    @Schema(example = "http://payments/success")
    private String paymentUrl;
    @Schema(example = "1")
    private String paymentSessionId;
    @Schema(example = "100")
    private BigDecimal paymentAmount;
}
