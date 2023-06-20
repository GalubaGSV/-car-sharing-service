package com.example.carsharingservice.strategy;

import com.example.carsharingservice.model.Rental;
import java.math.BigDecimal;
import java.time.Duration;
import org.springframework.stereotype.Component;

@Component
public class PaymentPriceCalculationStrategy implements PriceCalculationStrategy {
    @Override
    public BigDecimal calculatePrice(Rental rental) {
        long days = Duration.between(rental.getRentalDate(),
                rental.getReturnDate()).toDays();
        return rental.getCar().getDailyFee().multiply(BigDecimal.valueOf(days));
    }
}
