package com.example.carsharingservice.strategy;

import java.math.BigDecimal;
import com.example.carsharingservice.model.Rental;

public interface PriceCalculationStrategy {
    BigDecimal calculatePrice(Rental rental);
}
