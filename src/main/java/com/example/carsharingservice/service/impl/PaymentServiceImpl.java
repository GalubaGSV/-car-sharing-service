package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.PaymentType;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.PaymentRepository;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.RentalService;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private static final BigDecimal FINE_MULTIPLIER = BigDecimal.valueOf(1.5);
    private final PaymentRepository paymentRepository;
    private final RentalService rentalService;

    @Override
    public Payment add(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> findByRentalUserId(Long userId) {
        return paymentRepository.findByRental_User_Id(userId);
    }

    @Override
    public BigDecimal calculatePrice(Payment payment) {
        if (payment.getPaymentType().equals(PaymentType.PAYMENT)) {
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
        return BigDecimal.valueOf(days).multiply(fineAmount);
    }
}
