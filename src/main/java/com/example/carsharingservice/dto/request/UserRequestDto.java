package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Schema
public class UserRequestDto {
    @Schema(example = "bob@gmail.com")
    private String email;
    @Schema(example = "Bob")
    private String firstName;
    @Schema(example = "Bobchenko")
    private String lastName;
    @Schema(example = "12345678")
    private String password;
    @Enumerated(EnumType.STRING)
    @Schema(example = "CUSTOMER")
    private Role role;
}
