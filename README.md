# ShopFlow Payment Service

## Service Description

The ShopFlow Payment Service is responsible for processing payments for orders. It listens for order creation events from a Kafka topic and then simulates the payment process. It also provides RESTful endpoints for managing payments directly.

## Tech Stack

- **Java 17**
- **Spring Boot 2.7.5**
- **Spring Data JPA** for database interaction.
- **Spring for Apache Kafka** for event-driven communication.
- **PostgreSQL** as the primary database.
- **Maven** for dependency management.

## How to Run in Dev Mode

To run the service in development mode, you can use the following Maven command:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

This will start the service on port `8084` and connect to the development database and Kafka broker as configured in `src/main/resources/application-dev.properties`.

## API Endpoints

### Payments

- **GET /api/payments**

  Retrieves a list of all payments.

- **GET /api/payments/{id}**

  Retrieves a specific payment by its ID.

- **POST /api/payments**

  Creates a new payment.

- **PUT /api/payments/{id}**

  Updates an existing payment.

- **DELETE /api/payments/{id}**

  Deletes a payment by its ID.

## Communication with Other Services

The Payment Service communicates with other services asynchronously via Apache Kafka.

### Consumed Events

- **`order_created_events`**

  The service subscribes to the `order_created_events` Kafka topic. When it receives an order ID, it simulates payment processing and creates a new payment record in its database.
