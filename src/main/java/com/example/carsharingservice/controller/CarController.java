package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.service.CarService;
import java.util.List;
import com.example.carsharingservice.service.mapper.DtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final DtoMapper<CarRequestDto, CarResponseDto, Car> carMapper;

    @PostMapping
    public CarResponseDto add(@RequestBody CarRequestDto requestDto) {
        Car car = carService.add(carMapper.mapToModel(requestDto));
        return carMapper.mapToDto(car);
    }

    @GetMapping
    public List<CarResponseDto> getAll(@RequestParam(defaultValue = "20") Integer count,
                                       @RequestParam(defaultValue = "0") Integer page) {
        Pageable pageRequest = PageRequest.of(page, count);
        return carService.findAll(pageRequest).stream()
            .map(carMapper::mapToDto)
            .toList();
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
