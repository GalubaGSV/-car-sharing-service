package com.example.carsharingservice.strategy;

import java.math.BigDecimal;
import java.time.Duration;
import com.example.carsharingservice.model.Rental;
import org.springframework.stereotype.Component;

@Component
public class FinePriceCalculationStrategy implements PriceCalculationStrategy {
    private static final BigDecimal FINE_MULTIPLIER = BigDecimal.valueOf(1.5);

    @Override
    public BigDecimal calculatePrice(Rental rental) {
        if (rental.getActualReturnDate() == null) {
            throw new RuntimeException("No actual return date in rental");
        }
        long days = Duration.between(rental.getReturnDate(),
                rental.getActualReturnDate()).toDays();
        BigDecimal fineAmount = rental.getCar().getDailyFee().multiply(FINE_MULTIPLIER);
        return BigDecimal.valueOf(days).multiply(fineAmount);
    }
}
