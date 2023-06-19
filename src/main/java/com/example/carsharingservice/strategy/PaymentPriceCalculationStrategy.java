package com.example.carsharingservice.strategy;

import java.math.BigDecimal;
import com.example.carsharingservice.model.Rental;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
public class PaymentPriceCalculationStrategy implements PriceCalculationStrategy {
    @Override
    public BigDecimal calculatePrice(Rental rental) {
        long days = Duration.between(rental.getRentalDate(),
                rental.getReturnDate()).toDays();
        return rental.getCar().getDailyFee().multiply(BigDecimal.valueOf(days));
    }
}
