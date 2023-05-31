package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.lib.FieldsValueMatch;
import com.example.carsharingservice.lib.ValidEmail;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@FieldsValueMatch(field = "password", fieldMatch = "repeatPassword")
public class UserRegistrationDto {
    @ValidEmail
    private String email;
    @NotEmpty(message = "The password couldn't be empty")
    @Size(min = 8, message = "Password must be at least 8 symbols long")
    private String password;
    @NotEmpty(message = "The password couldn't be empty")
    @Size(min = 8, message = "Password must be at least 8 symbols long")
    private String repeatPassword;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
}
