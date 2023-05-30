package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Rental;
import java.util.List;

public interface RentalService {
    Rental add(Rental rental);

    List<Rental> getRentalsByUserIdAndStatus(Long id, boolean status);

    Rental get(Long id);

    Rental returnCarById(Long id, Rental rental);
}
