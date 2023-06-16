package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.lib.FieldsValueMatch;
import com.example.carsharingservice.lib.ValidEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema
@FieldsValueMatch(field = "password", fieldMatch = "repeatPassword")
public class UserRegistrationDto {
    @ValidEmail
    @Schema(example = "bob@gmail.com")
    private String email;
    @NotEmpty(message = "The password couldn't be empty")
    @Size(min = 8, message = "Password must be at least 8 symbols long")
    @Schema(example = "12345678")
    private String password;
    @NotEmpty(message = "The password couldn't be empty")
    @Size(min = 8, message = "Password must be at least 8 symbols long")
    @Schema(example = "12345678")
    private String repeatPassword;
    @Column(nullable = false)
    @Schema(example = "Bob")
    private String firstName;
    @Column(nullable = false)
    @Schema(example = "Bobchenko")
    private String lastName;
}
