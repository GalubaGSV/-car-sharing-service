package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.PaymentRepository;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.strategy.FinePriceCalculationStrategy;
import com.example.carsharingservice.strategy.PaymentPriceCalculationStrategy;
import com.example.carsharingservice.strategy.PriceCalculationStrategy;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final RentalService rentalService;
    private Map<Payment.PaymentType, PriceCalculationStrategy> strategyMap;

    public PaymentServiceImpl(PaymentRepository paymentRepository, RentalService rentalService) {
        this.paymentRepository = paymentRepository;
        this.rentalService = rentalService;
        strategyMap = new HashMap<>();
        strategyMap.put(Payment.PaymentType.PAYMENT, new PaymentPriceCalculationStrategy());
        strategyMap.put(Payment.PaymentType.FINE, new FinePriceCalculationStrategy());
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
        Rental rental = rentalService.get(payment.getRental().getId());
        payment.setRental(rental);
        PriceCalculationStrategy strategy = strategyMap.get(payment.getPaymentType());
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment type: "
                    + payment.getPaymentType());
        }
        return strategy.calculatePrice(payment.getRental());
    }
}
