package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.RentalRequestDto;
import com.example.carsharingservice.dto.response.RentalResponseDto;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.mapper.DtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;
    private final DtoMapper<RentalRequestDto, RentalResponseDto, Rental> rentalMapper;

    @PostMapping
    public RentalResponseDto add(@RequestBody RentalRequestDto rentalRequestDto) {
        return rentalMapper.mapToDto(rentalService
                .add(rentalMapper.mapToModel(rentalRequestDto)));
    }

    @GetMapping
    public List<RentalResponseDto> getRentalsByUserIdAndStatus(@RequestParam(name = "user_id") Long id,
                                                               @RequestParam(name = "is_active") boolean isActive) {
        return rentalService.getRentalsByUserIdAndStatus(id, isActive)
                .stream()
                .map(rentalMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public RentalResponseDto get(@PathVariable Long id) {
        return rentalMapper.mapToDto(rentalService.get(id));
    }

    @PostMapping("/{id}/return")
    public RentalResponseDto returnCar(@PathVariable Long id,
                                       @RequestBody RentalRequestDto rentalRequestDto) {
        return rentalMapper.mapToDto(rentalService
                .returnCarById(id, rentalMapper.mapToModel(rentalRequestDto)));
    }
}
