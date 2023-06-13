package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.model.CarType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Schema
public class CarRequestDto {
    @Schema(example = "X")
    private String model;
    @Schema(example = "Tesla")
    private String brand;
    @Enumerated(EnumType.STRING)
    @Schema(example = "UNIVERSAL")
    private CarType carType;
    @Schema(example = "10")
    private int inventory;
    @Schema(example = "100")
    private BigDecimal dailyFee;
}
