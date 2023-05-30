package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserRequestDto {
    private String email;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Role role;
}
