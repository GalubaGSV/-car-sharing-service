--liquibase formatted sql
--changeset sql:create-cars_table splitStatements:true endDelimiter:;
CREATE TABLE IF NOT EXISTS cars (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    car_type TINYINT NOT NULL,
    inventory INT NOT NULL,
    daily_fee DECIMAL(10, 2) NOT NULL,
    deleted TINYINT(1) NOT NULL DEFAULT 0
    );

--rollback DROP TABLE cars;
