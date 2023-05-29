--liquibase formatted sql
--changeset sql:create-payments_table splitStatements:true endDelimiter:;
CREATE TABLE IF NOT EXISTS payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    payment_status VARCHAR(255),
    payment_type VARCHAR(255),
    rental_id BIGINT,
    payment_url VARCHAR(255),
    payment_session_id VARCHAR(255),
    payment_amount DECIMAL(10, 2),
    FOREIGN KEY (rental_id) REFERENCES rentals(id)
    );

--rollback DROP TABLE payments;