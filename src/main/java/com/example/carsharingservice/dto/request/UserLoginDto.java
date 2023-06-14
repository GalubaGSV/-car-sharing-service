package com.example.carsharingservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema
public class UserLoginDto {
    @NotBlank(message = "Login can't be null or blank!")
    @Schema(example = "bob@gmail.com")
    private String login;
    @NotBlank(message = "Password can't be null or blank!")
    @Size(min = 8, message = "Password must be at least 8 symbols long")
    @Schema(example = "12345678")
    private String password;
}
