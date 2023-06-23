package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.UserLoginDto;
import com.example.carsharingservice.dto.request.UserRegistrationDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.exception.AuthenticationException;
import com.example.carsharingservice.mapper.UserMapper;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.security.jwt.JwtTokenProvider;
import com.example.carsharingservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @Operation(summary = "Data for registration", description = "Data for registration")
    @PostMapping("/register")
    public UserResponseDto register(
            @RequestBody @Valid UserRegistrationDto dto)
            throws AuthenticationException {
        User user = authenticationService.register(dto.getEmail(), dto.getPassword(),
                dto.getFirstName(), dto.getLastName());
        return userMapper.mapToDto(user);
    }

    @Operation(summary = "Data for login", description = "Data for login")
    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @RequestBody @Valid UserLoginDto dto)
            throws AuthenticationException {
        User user = authenticationService.login(dto.getLogin(),
                dto.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail(),
                List.of(user.getRole().name()));
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}
