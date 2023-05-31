package com.example.carsharingservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginDto {
    @NotBlank(message = "Login can't be null or blank!")
    private String login;
    @NotBlank(message = "Password can't be null or blank!")
    @Size(min = 8, message = "Password must be at least 8 symbols long")
    private String password;
}
