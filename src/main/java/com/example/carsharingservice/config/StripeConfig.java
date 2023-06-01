package com.example.carsharingservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@PropertySource("classpath:application.properties")
public class StripeConfig {
    @Value("${stripe.apikey}")
    private String stripeKey;

    @Value("${stripe.apikey}")
    private String secretKey;
}
