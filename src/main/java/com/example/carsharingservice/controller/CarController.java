package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.mapper.CarMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final CarMapper carMapper;

    @PostMapping
    public CarResponseDto add(@RequestBody CarRequestDto requestDto) {
        Car car = carService.add(carMapper.mapToModel(requestDto));
        return carMapper.mapToDto(car);
    }

    @GetMapping
    public List<CarResponseDto> getAll() {
        return carService.getAll().stream()
            .map(carMapper::mapToDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CarResponseDto get(@PathVariable Long id) {
        return carMapper.mapToDto(carService.get(id));
    }

    @PatchMapping("/{id}")
    public CarResponseDto update(@PathVariable Long id, @RequestBody CarRequestDto requestDto) {
        Car car = carService.update(id, carMapper.mapToModel(requestDto));
        return carMapper.mapToDto(car);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        carService.delete(id);
    }

}
