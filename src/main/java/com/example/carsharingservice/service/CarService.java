package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Car;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CarService {
    Car add(Car car);

    Car get(Long id);

    List<Car> findAll(Pageable pageable);

    Car update(Long id, Car car);

    void delete(Long id);
}
