package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Rental;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface RentalService {
    Rental add(Rental rental);

    List<Rental> getRentalsByUserIdAndStatus(Long id, boolean status, Pageable pageable);

    Rental get(Long id);

    Rental returnCar(Long id, Rental rental);

    List<Rental> getOverdueRentals();
}
