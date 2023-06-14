package com.example.carsharingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {
    @Operation(summary = "Health controller", description = "Health controller")
    @GetMapping
    public String healthCheck() {
        return "Success!";
    }
}
