package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Rental;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface RentalService {
    Rental add(Rental rental);

    List<Rental> getRentalsByUserIdAndStatus(Long id, boolean status, Pageable pageable);

    Rental get(Long id);

    Rental returnCar(Long id, Rental rental);

    List<Rental> getOverdueRentals();
}
