package com.example.carsharingservice.controller;

import com.example.carsharingservice.model.Payment;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @GetMapping("/?user_id={userId}")
    public List<Payment> getUserPayments(@RequestParam Long userId) {
        /**
         * todo add logic
         */

        return Collections.emptyList();
    }

    @PostMapping
    public void addPayment(@RequestBody Long userId) {
        /**
         * todo add logic
         * todo  return DTO instead void
         * todo  param DTO instead  long
         */
        return;
    }

    @GetMapping("/success")
    public void checkSuccessfulStripePayments() {
        /**
         * todo add logic
         */
        return;
    }

    @GetMapping("/cancel")
    public void returnPaymentPausedMessage() {
        /**
         * todo add logic
         */
        return;
    }

}
