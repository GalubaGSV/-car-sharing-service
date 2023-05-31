package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Payment;
import java.util.List;

public interface PaymentService {
    Payment add(Payment payment);

    List<Payment> findByRentalUserId(Long userId);
}
