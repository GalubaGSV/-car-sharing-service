--liquibase formatted sql
--changeset sql:create-rentals_table splitStatements:true endDelimiter:;
CREATE TABLE IF NOT EXISTS telegram_chats (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    chat_id BIGINT NOT NULL UNIQUE
    );
--rollback DROP TABLE rentals;
