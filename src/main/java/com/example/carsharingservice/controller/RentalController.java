package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.RentalRequestDto;
import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.RentalResponseDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.UserService;
import com.example.carsharingservice.service.impl.TelegramNotificationService;
import com.example.carsharingservice.service.mapper.DtoMapper;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;
    private final DtoMapper<RentalRequestDto, RentalResponseDto, Rental> rentalMapper;
    private final DtoMapper<UserRequestDto, UserResponseDto, User> userMapper;
    private final CarService carService;
    private final UserService userService;
    private final TelegramNotificationService telegramNotificationService;

    @Operation(summary = "Add rental", description = "Add rental")
    @PostMapping
    public RentalResponseDto add(
            @Parameter(schema = @Schema(type = "String",
                    defaultValue = "{\n"
                            + "    \"rentalDate\":\"2023-05-12T00:00:00.000Z\",\n"
                            + "    \"returnDate\":\"2023-06-12T00:00:00.000Z\",    \n"
                            + "    \"carId\":1, \n"
                            + "    \"userId\":1\n"
                            + "}"))
            @RequestBody RentalRequestDto rentalRequestDto) {
        Rental createdRental = rentalService.add(rentalMapper.mapToModel(rentalRequestDto));
        telegramNotificationService.sendMessage(String
                .format(
                                "New rental was created.\n"
                                        + "Rental info: %s\n"
                                        + "User info: %s\n"
                                        + "Car info: %s", rentalMapper.mapToDto(createdRental),
                        userMapper.mapToDto(userService.get(createdRental.getUser().getId())),
                        carService.get(createdRental.getCar().getId())), createdRental.getUser());
        return rentalMapper.mapToDto(createdRental);
    }

    @Operation(summary = "Get rental by user and status", description = "Get rental by user and status")
    @GetMapping
    public List<RentalResponseDto> getRentalsByUserIdAndStatus(
            @Parameter(description = "User id",
            schema = @Schema(type = "integer", defaultValue = "1"))
            @RequestParam(name = "user_id") Long id,
            @Parameter(description = "Status",
            schema = @Schema(type = "boolean", defaultValue = "true"))
            @RequestParam(name = "is_active") boolean isActive,
            @Parameter(description = "Rental per page",
            schema = @Schema(type = "integer"))
            @RequestParam(defaultValue = "20") Integer count,
            @Parameter(description = "Page number",
            schema = @Schema(type = "integer"))
            @RequestParam(defaultValue = "0") Integer page) {
        Pageable pageRequest = PageRequest.of(page, count);
        return rentalService.getRentalsByUserIdAndStatus(id, isActive, pageRequest)
                .stream()
                .map(rentalMapper::mapToDto)
                .toList();
    }

    @Operation(summary = "Get rental by id", description = "Get rental by id")
    @GetMapping("/{id}")
    public RentalResponseDto get(
            @Parameter(description = "Rental id",
            schema = @Schema(type = "integer", defaultValue = "1"))
            @PathVariable Long id) {
        return rentalMapper.mapToDto(rentalService.get(id));
    }

    @Operation(summary = "Set actual return date ", description = "Set actual return date ")
    @PostMapping("/{id}/return")
    public RentalResponseDto returnCar(
            @Parameter(description = "Rental id",
            schema = @Schema(type = "integer", defaultValue = "1"))
            @PathVariable Long id,
            @Parameter(schema = @Schema(type = "String",
                    defaultValue = "{\n"
                            + "  \"id\": 1,\n"
                            + "  \"rentalDate\":\"2023-06-01T23:39:42.979Z\",\n"
                            + "  \"returnDate\":\"2023-06-01T23:39:42.979Z\",\n"
                            + "  \"actualReturnDate\":\"2023-06-01T23:39:42.979Z\",\n"
                            + "  \"carId\": 1,\n"
                            + "  \"userId\": 1\n"
                            + "}"))
            @RequestBody RentalRequestDto rentalRequestDto) {
        Rental processedRental = rentalService
                .returnCar(id, rentalMapper.mapToModel(rentalRequestDto));
        telegramNotificationService.sendMessage(String
                .format(
                                "The car was returned.\n"
                                        + "Rental info: %s\n"
                                        + "User info: %s\n"
                                        + "Car info: %s\n", rentalMapper.mapToDto(processedRental),
                        userMapper.mapToDto(userService.get(processedRental.getUser().getId())),
                        carService.get(processedRental.getCar().getId())), processedRental.getUser());
        return rentalMapper.mapToDto(processedRental);
    }
}
