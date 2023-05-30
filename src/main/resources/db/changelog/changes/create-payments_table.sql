--liquibase formatted sql
--changeset sql:create-payments_table splitStatements:true endDelimiter:;
CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_status VARCHAR(255) NOT NULL,
    payment_type VARCHAR(255) NOT NULL,
    rental_id BIGINT,
    payment_url VARCHAR(255) NOT NULL,
    payment_session_id VARCHAR(255) NOT NULL,
    payment_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (rental_id) REFERENCES rentals(id)
    );
--rollback DROP TABLE payments;