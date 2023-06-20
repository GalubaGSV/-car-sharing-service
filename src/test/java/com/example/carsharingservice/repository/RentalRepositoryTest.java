package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.Rental;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RentalRepositoryTest {
    private static final boolean FALSE_STATUS = false;
    private static final boolean TRUE_STATUS = true;
    private static final Long USER_ID = 6L;
    private static final Pageable PAGE_REQUEST = PageRequest.of(0, 5);
    @Container
    static MySQLContainer<?> database = new MySQLContainer<>("mysql")
            .withDatabaseName("car_sharing_service")
            .withPassword("rental_test")
            .withUsername("rental");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    private RentalRepository rentalRepository;

    @Test
    @Sql("/scripts/init_car.sql")
    @Sql("/scripts/init_user.sql")
    @Sql("/scripts/init_three_rentals.sql")
    void getRentalsByUserIdAndStatus_trueStatus_ok() {
        List<Rental> actual = rentalRepository
                .getRentalsByUserIdAndStatus(USER_ID, TRUE_STATUS, PAGE_REQUEST);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals("2023-06-11T21:00", actual.get(0).getRentalDate().toString());
    }

    @Test
    @Sql("/scripts/init_car.sql")
    @Sql("/scripts/init_user.sql")
    @Sql("/scripts/init_three_rentals.sql")
    void getRentalsByUserIdAndStatus_falseStatus_ok() {
        List<Rental> actual = rentalRepository
                .getRentalsByUserIdAndStatus(USER_ID, FALSE_STATUS, PAGE_REQUEST);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals("2023-06-12T14:00", actual.get(0).getActualReturnDate().toString());
    }

    @Test
    @Sql("/scripts/init_car.sql")
    @Sql("/scripts/init_user.sql")
    @Sql("/scripts/init_three_rentals.sql")
    void getOverdueRentals_ok() {
        List<Rental> actual = rentalRepository
                .getOverdueRentals(LocalDateTime.of(2023, 6, 27, 3, 0, 0));
        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals("2023-05-11T21:00", actual.get(0).getRentalDate().toString());
    }
}
