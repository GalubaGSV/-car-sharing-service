package com.example.carsharingservice.strategy;

import com.example.carsharingservice.model.Rental;
import java.math.BigDecimal;

public interface PriceCalculationStrategy {
    BigDecimal calculatePrice(Rental rental);
}
