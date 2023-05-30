package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.repository.PaymentRepository;
import com.example.carsharingservice.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    @Override
    public Payment add(Payment payment) {
        return paymentRepository.save(payment);
    }
}
