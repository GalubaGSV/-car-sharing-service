package com.example.carsharingservice.dto.response;

import com.example.carsharingservice.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Schema
public class UserResponseDto {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "email@gmail.com")
    private String email;
    @Schema(example = "Name")
    private String firstName;
    @Schema(example = "Last Name")
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Schema(example = "CUSTOMER")
    private Role role;
}
