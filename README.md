# Bodega Francisco API

A microservices-based backend system designed to manage warehouse operations, built with a 
strong focus on **distributed architecture, scalability, and enterprise-grade practices**.

---

## Overview

**Bodega Francisco API** is a backend system that implements a **microservices architecture** 
to handle core business domains such as authentication, customers, products, and orders.

This project is designed to showcase:

- Distributed system design
- Domain-driven service separation
- Inter-service communication (event-driven)
- JWT-based authentication & authorization
- Polyglot persistence (MongoDB + PostgreSQL)

---

## Architecture

The system follows a **microservices architecture** with an **API Gateway** acting as the 
single entry point.

### Core Services

- **API Gateway**
    - Centralized routing
    - Entry point for all client requests

- **Auth Service**
    - User registration & authentication
    - JWT generation
    - Role management

- **Customer Service**
    - Customer management
    - Kafka-based event integration

- **Product Service**
    - Product management
    - PostgreSQL persistence

- **Order Service**
    - Order management (base structure implemented)

- **Common Module**
    - Shared DTOs and utilities
    - Standardized API responses
    - Error handling

---

## Tech Stack

- **Java + Spring Boot**
- **Spring Security + JWT**
- **Spring Cloud Gateway (MVC)**
- **Apache Kafka**
- **MongoDB** (Auth & Customer)
- **PostgreSQL** (Products & Orders)
- **Docker / Docker Compose**
- **Maven (Multi-module)**

---

## Security

- JWT-based authentication
- Roles embedded in JWT claims
- Custom authentication filters
- Role-based access control (`@PreAuthorize`)

---

## Inter-Service Communication

- Event-driven architecture using **Apache Kafka**

### Example flow implemented:
1. User registers → Auth Service
2. Event is published to Kafka
3. Customer Service consumes event → creates customer
4. System stays loosely coupled

---

## API Gateway Endpoints

All requests must go through the **API Gateway**.

> Base URL example:
```
http://localhost:8080/bodegasfranc/api
```

### Auth Routes

```
POST /auth/register
POST /auth/login
```

---

### Customer Routes

```
GET    /customers
GET    /customers/{id}
POST   /customers
PUT    /customers/{id}
DELETE /customers/{id}
```

---

### Product Routes

```
GET    /products
GET    /products/{id}
POST   /products
PUT    /products/{id}
DELETE /products/{id}
```

---

### Order Routes (Partial)

```
GET    /orders
POST   /orders
```

> Note: Order logic is still under development.

---

## Project Structure

```text
bodega-francisco-api/
│
├── api-gateway/
├── auth-service/
├── customer-service/
├── product-service/
├── order-service/
├── common/
├── event-contracts/
└── doc/
    └── ARCHITECTURE.md
```

> The `/doc` folder contains architecture diagrams and design decisions.

---

## Current Status

### Implemented

- JWT authentication
- Functional API Gateway
- Auth ↔ Customer integration
- Kafka-based communication (basic flow)
- MongoDB & PostgreSQL persistence

### In Progress

- Full order lifecycle
- Advanced inter-service orchestration
- Security hardening and validations

---

## Running with Docker

```Bash
docker compose up --build
```

### Included services:

- Kafka + Zookeeper
- MongoDB (Auth & Customer)
- PostgreSQL (Products & Orders)
- All microservices

---

## Project Goal

This project is part of a professional portfolio focused on:

- Enterprise backend development
- Microservices architecture
- Distributed systems design
- Clean Code & domain separation

---

## Author

**Kevin Alejandro Francisco González**  
Backend Developer specialized in Java and system architecture

---

## Future Improvements

- Saga Pattern implementation
- Distributed tracing & observability
- Rate limiting at API Gateway
- Automated testing (unit & integration)
- CI/CD pipelines
