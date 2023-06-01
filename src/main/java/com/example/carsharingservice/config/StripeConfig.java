package com.example.carsharingservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@PropertySource("classpath:application.properties")
public class StripeConfig {
    @Value("${stripe.apikey}")
    private String secretKey;
}
