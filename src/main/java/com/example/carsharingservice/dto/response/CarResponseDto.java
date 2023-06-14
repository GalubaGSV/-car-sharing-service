package com.example.carsharingservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class CarResponseDto {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "Model")
    private String model;
    @Schema(example = "Brand")
    private String brand;
    @Schema(example = "UNIVERSAL")
    private String carType;
    @Schema(example = "10")
    private int inventory;
    @Schema(example = "100 USD")
    private String dailyFee;
    @Schema(example = "0")
    private boolean deleted;
}
