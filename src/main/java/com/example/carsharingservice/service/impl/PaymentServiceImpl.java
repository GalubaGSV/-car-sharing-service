package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.PaymentType;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.PaymentRepository;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.RentalService;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.carsharingservice.strategy.FinePriceCalculationStrategy;
import com.example.carsharingservice.strategy.PaymentPriceCalculationStrategy;
import com.example.carsharingservice.strategy.PriceCalculationStrategy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final RentalService rentalService;
    private Map<PaymentType, PriceCalculationStrategy> strategyMap;

    public PaymentServiceImpl(PaymentRepository paymentRepository, RentalService rentalService) {
        this.paymentRepository = paymentRepository;
        this.rentalService = rentalService;
        strategyMap = new HashMap<>();
        strategyMap.put(PaymentType.PAYMENT, new PaymentPriceCalculationStrategy());
        strategyMap.put(PaymentType.FINE, new FinePriceCalculationStrategy());
    }

    @Override
    public Payment add(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> findByRentalUserId(Long userId, Pageable pageRequest) {
        return paymentRepository.findByRental_User_Id(userId, pageRequest);
    }

    @Override
    public BigDecimal calculatePrice(Payment payment) {
        /*if (payment.getPaymentType().equals(PaymentType.PAYMENT)) {
            Rental rental = rentalService.get(payment.getRental().getId());
            payment.setRental(rental);
            long days = Duration.between(rental.getRentalDate(),
                    rental.getReturnDate()).toDays();
            return rental.getCar().getDailyFee().multiply(BigDecimal.valueOf(days));
        }
        Rental rental = rentalService.get(payment.getRental().getId());
        payment.setRental(rental);
        long days = Duration.between(rental.getActualReturnDate(),
                rental.getReturnDate()).toDays();
        BigDecimal fineAmount = rental.getCar().getDailyFee().multiply(FINE_MULTIPLIER);
        return BigDecimal.valueOf(days).multiply(fineAmount);*/

        Rental rental = rentalService.get(payment.getRental().getId());
        payment.setRental(rental);
        PriceCalculationStrategy strategy = strategyMap.get(payment.getPaymentType());
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment type: " + payment.getPaymentType());
        }
        return strategy.calculatePrice(payment.getRental());
    }
}
