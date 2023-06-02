package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.mapper.DtoMapper;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final DtoMapper<CarRequestDto, CarResponseDto, Car> carMapper;

    @Operation(summary = "Add car", description = "Creation of the essence of the car")
    @PostMapping
    public CarResponseDto add(
            @Parameter(schema = @Schema(type = "String",
                    defaultValue = "{\n"
                            + "    \"model\":\"X\", \n"
                            + "    \"brand\":\"Tesla\", \n"
                            + "    \"carType\":\"UNIVERSAL\",\n"
                            + "    \"inventory\":10, \n"
                            + "    \"dailyFee\":100\n"
                            + "}"))
            @RequestBody CarRequestDto requestDto) {
        Car car = carService.add(carMapper.mapToModel(requestDto));
        return carMapper.mapToDto(car);
    }

    @Operation(summary = "Get all car", description = "List of all car")
    @GetMapping
    public List<CarResponseDto> getAll(
            @Parameter(description = "Car per page",
            schema = @Schema(type = "integer"))
            @RequestParam(defaultValue = "20") Integer count,
            @Parameter(description = "Coun of page",
            schema = @Schema(type = "integer"))
            @RequestParam(defaultValue = "0") Integer page) {
        Pageable pageRequest = PageRequest.of(page, count);
        return carService.findAll(pageRequest).stream()
            .map(carMapper::mapToDto)
            .toList();
    }

    @Operation(summary = "Get car by id", description = "Get car by id")
    @GetMapping("/{id}")
    public CarResponseDto get(
            @Parameter(description = "Car id",
            schema = @Schema(type = "integer", defaultValue = "1"))
            @PathVariable Long id) {
        return carMapper.mapToDto(carService.get(id));
    }

    @Operation(summary = "Update car by id", description = "Update car by id")
    @PatchMapping("/{id}")
    public CarResponseDto update(
            @Parameter(description = "Car id",
            schema = @Schema(type = "integer", defaultValue = "1"))
            @PathVariable Long id,
            @Parameter(schema = @Schema(type = "String",
                    defaultValue = "{\n"
                            + "    \"model\":\"X\", \n"
                            + "    \"brand\":\"Tesla\", \n"
                            + "    \"carType\":\"UNIVERSAL\",\n"
                            + "    \"inventory\":10, \n"
                            + "    \"dailyFee\":100\n"
                            + "}"))
            @RequestBody CarRequestDto requestDto) {
        Car car = carService.update(id, carMapper.mapToModel(requestDto));
        return carMapper.mapToDto(car);
    }

    @Operation(summary = "Delete car by id", description = "Delete car by id")
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "Car id",
            schema = @Schema(type = "integer", defaultValue = "1"))
            @PathVariable Long id) {
        carService.delete(id);
    }
}
