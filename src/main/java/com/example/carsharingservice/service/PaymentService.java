package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Payment;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    Payment add(Payment payment);

    List<Payment> findByRentalUserId(Long userId, Pageable pageRequest);

    BigDecimal calculatePrice(Payment payment);
}
