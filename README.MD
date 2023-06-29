# 🚘 CAR SHARING SERVICE 🚘

![img.png](src/main/resources/docs/img.png)

## 📄 Project Description

There is a car-sharing service in our city, where you can rent cars and pay for your usage using cash, depending on the
rental duration.
The problem is, the system for tracking cars, rentals, users, and payments in the car-sharing service is outdated -
everything is managed manually and all records are kept on paper. There is no way to check the availability of specific
cars in the service. Additionally, you can only make payments with cash; there is no support for credit cards. The
car-sharing service administration has no way of knowing who returned the car on time and who did not.In this project,
we solve these issues.

The main task of the project is the implementation of an online car rental management system. This system will
streamline the
work of the service administrators and greatly enhance the user experience.

## 🛠 Functional (what the system should do)

- Web-based
- Manage car sharing inventory
- Manage car rentals
- Manage customers
- Display notifications
- Handle payments
- Telegram notification

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
  - POST: `/login` - Login to the system, receive a token

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
  - POST: `/rentals` - add a new rental
  - GET: `/rentals` - get rentals by userId and
  - GET: `/rentals/{id}` - get rental by
  - POST: `/rentals/{id}/return` - is called to process a car return

- Payments Controller
  - GET:    `/payments`    - get user's payments
  - POST:    `/payments`    - create payment session
  - GET:    `/payments/success`    - check successful Stripe payments (Endpoint for stripe redirection)
  - GET:    `/payments/cancel`    - return payment paused message

## Telegram notifications

In this project, we notify clients and managers using a Telegram bot.
Notifications come when leases are created, leases are overdue, and payments are successful

## Getting Started

## 💻How to Run and Test with Docker
⚠️Important: You must have Docker installed. If it is not installed, please download it from the website [link](https://www.docker.com/products/docker-desktop/) and proceed with the installation.

* Clone the repo on GitHub
* Run docker client
* Build the project: `mvn clean package`
* In the terminal, run the command: `docker-compose up`
* Use the address `http://localhost:6868/swagger-ui/index.html#/` to access the documentation.

#### To get started with the car-sharing service, follow these steps:

- Clone the repository
- You need to get a token to create a Telegram-bot `https://t.me/BotFather`
- Set your credentials in `application.properties`
- Set up the necessary environment variables. Refer to `.env` for the required variables
- Build the project: mvn compile
- Run the application: npm start or yarn start.
  You can test the operation of the application using swagger using
  address `http://localhost:8080/swagger-ui/index.html#/`

## 🛠 Technologies 🛠
* Java `17`
* Apache Maven `3.10.1`
* Apache Tomcat  `9.0.73`
* PostgreSQL `42.5.4`
* Spring:
  * Boot `3.0.6`
  * Data Jpa `3.0.6`
  * Web Mvc `6.0.8`
* Liquibase-core `4.17.2`
* lombok `1.18.26`
* Hibernate `6.1.7.Final`
* Swagger UI
* SpringDoc `2.1.0`
* Checkstyle Plugin `3.1.1`

## Conclusions

By implementing these features in an online car rental management system, the service can automate processes,
provide real-time information, and offer a more convenient and efficient experience for both users and administrators.

## Our team Contacts
If you have any questions or suggestions, please feel free to contact us:

* Sergiy Golubchenko: [GitHub](https://github.com/GalubaGSV) |  [LinkedIn](https://www.linkedin.com/in/sergiy-golubchenko-74646485/)
* Mariana Polishchuk: [GitHub](https://github.com/marianapolishchuk2169) |  [LinkedIn](https://www.linkedin.com/in/mariana-polishchuk-42b049206/)
* Oleksandr Lutsenko: [GitHub](https://github.com/AleksandrLts) |  [LinkedIn](https://www.linkedin.com/in/oleksandr-lutsenko-4a8b8923b/)
* Denys Dominskyi: [GitHub](https://github.com/Dominskyi93) |  [LinkedIn](https://www.linkedin.com/in/denys-dominskyi-6ab182259/)
* Maksym Yashyn: [GitHub](https://github.com/Nimakel) |  [LinkedIn](https://www.linkedin.com/in/maksym-yashyn-dnipro/)
