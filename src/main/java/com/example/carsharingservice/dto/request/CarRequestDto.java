package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.model.CarType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarRequestDto {
    private String model;
    private String brand;
    @Enumerated(EnumType.STRING)
    private CarType carType;
    private int inventory;
    private BigDecimal dailyFee;
}
