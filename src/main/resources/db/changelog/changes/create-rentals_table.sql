--liquibase formatted sql
--changeset sql:create-rentals_table splitStatements:true endDelimiter:;
CREATE TABLE IF NOT EXISTS rentals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    car_id BIGINT,
    user_id BIGINT,
    rental_date DATETIME(6) NOT NULL,
    return_date DATETIME(6) NOT NULL,
    actual_return_date DATETIME(6),
    total_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (car_id) REFERENCES cars(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
    );




--rollback DROP TABLE rentals;