package com.example.carsharingservice.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarResponseDto {
    private Long id;
    private String model;
    private String brand;
    private String carType;
    private int inventory;
    private String dailyFee;
    private boolean deleted;
}
