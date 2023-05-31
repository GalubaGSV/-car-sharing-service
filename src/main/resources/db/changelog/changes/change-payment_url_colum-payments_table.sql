--liquibase formatted sql
--changeset sql:modify-payments_table splitStatements:true endDelimiter:;
ALTER TABLE payments
    MODIFY COLUMN payment_url VARCHAR(500) NOT NULL;
