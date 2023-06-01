package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Car;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CarService {
    Car add(Car car);

    Car get(Long id);

    List<Car> findAll(Pageable pageable);

    Car update(Long id, Car car);

    void delete(Long id);
}
