package com.example.carsharingservice.service.mapper;

import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper implements DtoMapper<PaymentRequestDto, PaymentResponseDto, Payment> {
    @Override
    public Payment mapToModel(PaymentRequestDto dto) {
        Payment payment = new Payment();
        payment.setPaymentAmount(dto.getPaymentAmount());
        payment.setPaymentType(dto.getPaymentType());
        payment.setPaymentStatus(dto.getPaymentStatus());
        Rental rental = new Rental();
        rental.setId(dto.getRentalId());
        payment.setRental(rental);
        payment.setPaymentSessionId(dto.getPaymentSessionId());
        payment.setPaymentUrl(dto.getPaymentUrl());
        return payment;
    }

    @Override
    public PaymentResponseDto mapToDto(Payment model) {
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setId(model.getId());
        paymentResponseDto.setPaymentAmount(model.getPaymentAmount());
        paymentResponseDto.setPaymentStatus(model.getPaymentStatus().getValue());
        paymentResponseDto.setPaymentType(model.getPaymentType().getValue());
        paymentResponseDto.setRentalId(model.getRental().getId());
        paymentResponseDto.setPaymentSessionId(model.getPaymentSessionId());
        paymentResponseDto.setPaymentUrl(model.getPaymentUrl());
        return paymentResponseDto;
    }
}
