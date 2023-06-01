# CAR SHARING SERVICE

## Project Description

There is a car-sharing service in our city, where you can rent cars and pay for your usage using cash, depending on the
rental duration.
The problem is, the system for tracking cars, rentals, users, and payments in the car-sharing service is outdated -
everything is managed manually and all records are kept on paper. There is no way to check the availability of specific
cars in the service. Additionally, you can only make payments with cash; there is no support for credit cards. The
car-sharing service administration has no way of knowing who returned the car on time and who did not.In this project,
we solve these issues.

The main task of which is the implementation of an online car rental management system. This system will streamline the
work of the service administrators and greatly enhance the user experience.

## Requirements

### Functional (what the system should do)

- Web-based
- Manage car sharing inventory
- Manage car rentals
- Manage customers
- Display notifications
- Handle payments

### Non-functional (what the system should deal with)

- Support up to 5 concurrent users
- Manage up to 1000 cars
- Handle 50,000 rentals per year
- Approximately 30MB of data per year

## Architecture

- `config` - this package contains configuration files.
- `controller` - this package contains the controllers.
- `dto` - this package contains data transfer objects that are used to encapsulate and transfer data between the
  different layers of the application. These objects help to unify requests and responses in the controllers.
- `exception` - this package contains custom exceptions.
- `lib` - this package contains custom annotations for validation email and password.
- `model` - this package contains the model for the database. This model is used to represent data entities in the
  database and is used by the DAO to map database records to Java objects.
- `repository` - this package contains the data access layer (also known as the repository layer) that is responsible
  for accessing and manipulating data in the database.
- `security` - this package contains security settings.
- `service` - this package contains the services that call the repositories. These services are responsible for
  performing business logic and coordinating the interactions between the controllers and the DAO.
- `telegrambot` - this package contains Telegram bot settings.

#### User (Customer)

- `email`
- `firstName`
- `lastName`
- `password`
- `role` (Enum: `MANAGER` | `CUSTOMER`)

#### Rental

- `rentalDate`
- `returnDate`
- `actualReturnDate`
- `carId`
- `userId`

#### Payment

- `paymentStatus` (Enum: `PENDING` | `PAID`)
- `paymentType`: (Enum: `PAYMENT` | `FINE`)
- `rental`
- `paymentUrl`
- `paymentSessionId`
- `paymentAmount`

### Controllers

- Authentication Controller
    - POST: `/register` - register a new user
    - POST: `/login` - Login to the system, receive a token.

- Users Controller
    - PUT: `/users/{id}/role` - update user role
    - GET: `/users/me` - get my profile info
    - PUT: `/users/me` - update profile info

- Cars Controller
    - POST: `/cars` - add a new car
    - GET: `/cars` - get a list of cars
    - GET: `/cars/{id}` - get car's detailed information by id
    - PATCH: `/cars/{id}`- update car (also manage inventory)
    - DELETE: `/cars/{id}` - delete car ин шв

- Rentals Controller
    - POST: rentals/ - add a new rental (decrease car inventory by 1)
    - GET: rentals/?user_id=...&is_active=... - get rentals by user ID and whether the rental is still active
