package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.repository.CarRepository;
import com.example.carsharingservice.service.CarService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Override
    public Car add(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car get(Long id) {
        return carRepository.getReferenceById(id);
    }

    @Override
    public List<Car> findAll(Pageable pageRequest) {
        return carRepository.findAll(pageRequest).toList();
    }

    @Override
    public Car update(Long id, Car car) {
        Car carFromDb = carRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no car with id: " + id));
        carFromDb.setModel(car.getModel());
        carFromDb.setBrand(car.getBrand());
        carFromDb.setInventory(car.getInventory());
        carFromDb.setDailyFee(car.getDailyFee());
        carFromDb.setCarType(car.getCarType());
        return carRepository.save(carFromDb);
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }
}
