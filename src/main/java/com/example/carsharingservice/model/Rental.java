package com.example.carsharingservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rental_date", nullable = false)
    private LocalDateTime rentalDate;
    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;
    @Column(name = "actual_return_date")
    private LocalDateTime actualReturnDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Car car;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
}
