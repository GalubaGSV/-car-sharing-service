--liquibase formatted sql

--changeset galuba:id1
INSERT INTO users (id, email, first_name, last_name, password, role, chat_id)
VALUES (1, 'bob@gmail.com', 'Bob', 'Bobson', '$2a$10$vXmLsNp9QXjRrszQ.511qeTCH2BVxD/Qu8VU/HufkZzYL/Qd0AAsC', 'MANAGER', null);
--rollback DELETE FROM users WHERE id = 1;

--changeset galuba:id2
INSERT INTO users (id, email, first_name, last_name, password, role, chat_id)
VALUES (2, 'alice@gmail.com', 'Alice', 'Alison', '$2a$10$vXmLsNp9QXjRrszQ.511qeTCH2BVxD/Qu8VU/HufkZzYL/Qd0AAsC', 'CUSTOMER', null);
--rollback DELETE FROM users WHERE id = 2;


--changeset galuba:id3
INSERT INTO cars (id, model, brand, car_type, inventory, daily_fee, deleted)
VALUES (1, 'Cybertruck', 'Tesla', 'SEDAN', 10, 200, false);
--rollback DELETE FROM cars WHERE id = 1;

--changeset galuba:id4
INSERT INTO cars (id, model, brand, car_type, inventory, daily_fee, deleted)
VALUES (2, 'C257', 'Mercedes', 'SEDAN', 10, 200, false);
--rollback DELETE FROM cars WHERE id = 2;

--changeset galuba:id5
INSERT INTO cars (id, model, brand, car_type, inventory, daily_fee, deleted)
VALUES (3, 'X5', 'BMW ', 'SEDAN', 10, 200, false);
--rollback DELETE FROM cars WHERE id = 2;


--changeset galuba:id6
INSERT INTO rentals (id, rental_date, return_date, actual_return_date, car_id, user_id)
VALUES (1, '2023-05-11 21:00:00', '2023-06-11 21:00:00 ', null, 1, 2);
--rollback DELETE FROM rentals WHERE id = 1;

--changeset galuba:id7
INSERT INTO rentals (id, rental_date, return_date, actual_return_date, car_id, user_id)
VALUES (2, '2023-05-15 21:00:00', '2023-06-11 21:00:00 ', null, 2, 2);
--rollback DELETE FROM rentals WHERE id = 2;

--changeset galuba:id8
INSERT INTO rentals (id, rental_date, return_date, actual_return_date, car_id, user_id)
VALUES (3, '2023-05-15 21:00:00', '2023-06-20 21:00:00 ', '2023-07-20 21:00:00', 3, 2);
--rollback DELETE FROM rentals WHERE id = 3;
