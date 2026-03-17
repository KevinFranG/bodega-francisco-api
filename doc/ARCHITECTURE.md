# Bodegas Francisco Api

Online store that distributes and offers products to customers in different regions.

## Index

- [System Overview](#1-system-overview)
- [System Actors](#2-system-actors)
- [Main Use Cases](#3-main-use-cases)
- [Domain Boundaries](#4-domain-boundaries)
- [Microservices Architecture](#5-microservices-architecture)
- [Event Drive Architecture](#6-event-drive-architecture)
- [System Lifecycle](#7-system-lifecycle)
- [Data Architecture](#8-data-architecture)
- [Security Architecture](#9-security-architecture)
- [Observability & Monitoring](#10-observability--monitoring)
- [Deployment Architecture](#11-deployment-architecture)
- [Architecture Decisions & Trade-offs](#12-architecture-decisions--trade-offs)

---

## 1. System Overview

Bodegas Francisco Commerce Platform SA. de CV.

### Problematic

Bodegas Francisco is a regional distribution business that requires a scalable digital platform to manage product sales,
inventory, customer orders, credit purchases, and warehouse logistics across multiple regions.

### Goals:

- Allow customers to browse and purchase products online
- Manage customers credits and loans
- Manage inventory across warehouses
- Process orders and payments reliably
- Provide analytics and business insights
- Support asynchronous integrations between services

### Non functional goals:

- Scalability
- High availability
- Fault tolerance
- Event-driven communication
- Observability
- Secure authentication and authorization

---

## 2. System Actors

### Customer

A user who purchases products through the online platform.

### Warehouse worker

Responsible for preparing and shipping customer orders.

### Administrator

Manages products, categories, and system configurations.

### Data analyst

Accesses analytics and reports to understand sales performance and business metrics.

---

## 3. Main use cases

### Customer

- Register and login
- Browse product catalog
- View product details
- Add products to cart
- Place an order
- Receive notifications about order status
- View order history
- Request credit purchases

### Warehouse worker

- View pending orders
- Prepare products for shipment
- Update shipment status
- Check products stock

### Administrator

- Create and update products
- Manage categories
- Monitor inventory
- Manage users
- Create and update discounts
- Manage debtors

### Data analyst

- View sales statistics
- Analyze top-selling products
- Analyze customers behavior

---

## 4. Domain Boundaries

### Identity & access

- User authentication
- Token generation
- Role-based authorization
- Session management

### User domain

- User profiles
- Addresses
- Customer information

### Product catalog domain

- Product information
- Categories
- Product description
- Product search

### Inventory domain

- Product stock levels
- Inventory reservations
- Stock movements
- Warehouse visibility

### Order domain

- Order creation
- Order lifecycle
- Order history
- Order cancellation

### Payment domain

- Payment request
- Payment confirmation
- Payment failures

### Notification domain

- Email notifications
- WebSocket notifications
- System alerts

### Analytic domain

- Sales metrics
- Product performance
- Customer insights
- Business dashboards

---

## 5. Microservices Architecture

### API Gateway

The API Gateway is the single entry point for all client requests.
It handles routing, authentication validation, rate limiting and request forwarding to the appropriate microservices.

Responsibilities:

- Request routing
- JWT validation
- Rate limiting
- API aggregation

### Auth service

Responsible for user authentication and authorization.
It manages login, token generation, and session validation.

Responsibilities:

- User login
- Token generation
- Token validation
- OAuth integration

Technologies:

- JWT
- OAuth2
- Keycloak

### User service

Manages user profiles and customer information.

Responsibilities:

- User profiles
- Addresses
- Customer preferences
- User purchases

### Product catalog service

Responsible for managing product data and categories.

Responsibilities:

- Product creation
- Product updates
- Product listing
- Product search

Technologies:

- Elasticsearch (product query)

### Inventory service

Handles stock levels and product availability.

Responsibilities:

- Track product stock
- Reserve inventory
- Release inventory

### Order Service

Manages customer orders and the order lifecycle.

Responsibilities:

- Create orders
- Cancel orders
- Track order status
- Maintain order history

### Payment Service

Processes payment requests and payment confirmations.

Responsibilities:

- Process payments
- Handle payment failures
- Payment webhooks

### Notification Service

Responsible for sending notifications to users.

Types:

- Email notifications
- WebSocket notifications
- System alerts

### Analytics Service

Processes business events and generates metrics and reports.

Responsibilities:

- Sales analytics
- Product performance metrics
- Customer insights

### Service Communication

The system uses a hybrid communication model.

Synchronous communication:

- REST APIs
- GraphQL queries

Asynchronous communication:

- Event-driven messaging using Apache Kafka

---

## 6. Event-drive Architecture

The platform uses an event-driven architecture to allow services to communicate asynchronously.
This approach improves scalability, resilience and decoupling between services.

Events are published to a message broker and consumed by interested services.

### Event Broker

The system uses Apache Kafka as the event streaming platform.
Kafka allows services to publish and consume events without tight coupling.

### Order Creation Flow

1. Customer places an order
2. Order Service creates the order
3. Order Service publishes an OrderCreated event
4. Multiple services consume the event

Services at stake:

1. Inventory Service → reserves stock
2. Payment Service → processes payment
3. Notification Service → notifies the user
4. Analytics Service → records the sale

### Core system events

| Event             | Consumers                                                                   |
|-------------------|-----------------------------------------------------------------------------|
| UserRegistered    | Notification Service, Analytics Service                                     |
| ProductCreated    | Product Service, Analytics Service, Search Index                            |
| ProductUpdated    | Product Service, Search Index                                               |
| ProductOutOfStock | Notification Service, Analytics Service                                     |
| CartCheckedOut    | Order Service                                                               |
| OrderConfirmed    | Notification Service, Analytics Service                                     |
| OrderCreated      | Inventory Service, Payment Service, Notification Service, Analytics Service |
| OrderCancelled    | Inventory Service, Notification Service, Analytics Service                  |
| OrderCompleted    | Notification Service, Analytics Service                                     |
| PaymentRequested  | Payment Service                                                             |
| PaymentCompleted  | Order Service, Notification Service, Analytics Service                      |
| PaymentFailed     | Order Service, Notification Service, Analytics Service                      |
| InventoryReserved | Order Service                                                               |
| InventoryReleased | Order Service                                                               |
| OrderShipped      | Notification Service, Analytics Service                                     |
| OrderDelivered    | Notification Service, Analytics Service                                     |
| CartCreated       | User Service                                                                |
| CartItemAdded     | User Service                                                                |
| CartItemRemoved   | User Service                                                                |

### Example event payload

Order created
...

---

## 7. System Lifecycle

### Order states

| State           | Description                                                        |
|-----------------|--------------------------------------------------------------------|
| CREATED         | The order has been created but payment has not yet been processed. |
| PENDING_PAYMENT | The payment request has been initiated.                            |
| PAID            | Payment was successful.                                            |
| PROCESSING      | Warehouse is preparing the order.                                  |
| SHIPPED         | The order has been sent to the customer.                           |
| DELIVERED       | The order has reached the customer.                                |
| CANCELLED       | The order was cancelled by the user or system.                     |
| FAILED          | The order failed due to payment or inventory issues.               |

### Flow

```text
Customer places order
        │
        ▼
OrderCreated
        │
        ▼
InventoryReserved
        │
        ▼
PaymentRequested
        │
        ▼
PaymentCompleted
        │
        ▼
OrderConfirmed
        │
        ▼
PROCESSING
        │
        ▼
OrderShipped
        │
        ▼
OrderDelivered
```

### Failure scenarios

Payment Failure:
```text
PaymentFailed
        │
        ▼
OrderCancelled
        │
        ▼
InventoryReleased
```

Inventory Failure
```text
InventoryUnavailable
        │
        ▼
OrderCancelled
```

### Saga pattern

The order workflow is implemented using the Saga Pattern to maintain data consistency across multiple services.
Each service performs a local transaction and publishes an event that triggers the next step in the workflow.

#### Compensating transactions

If any step fails, compensating actions are triggered to revert previous operations.

| Step              | Action            | Compensation     |
|-------------------|-------------------|------------------|
| Create Order      | OrderCreated      | CancelOrder      |
| Reserve Inventory | InventoryReserved | ReleaseInventory |
| Process Payment   | PaymentCompleted  | RefundPayment    |

There are two approaches to implementing the Saga Pattern:

1. Orchestration
   A central service coordinates the workflow.

2. Choreography
   Each service reacts to events and triggers the next step.

This platform uses a choreography-based saga approach using event streaming using Kafka.

---

## 8. Data Architecture

Each microservice owns its data and manages its own database.
Services must not access another service's database directly.

Data is shared across services through APIs or events.

### Auth service

Database: PostgreSQL

Data:

- user credentials
- tokens
- roles.

### User service

Database: MongoDB

Data:

- UserProfiles
- Addresses
- CustomerPreferences

### Product catalog service

Database: PostgreSQL

Data:

- Products
- Categories
- ProductImages

### Inventory service

Database: PostgreSQL

Data:

- Inventory
- StockMovements
- Reservations

### Order service

Database: PostgreSQL

Data:

- Orders
- OrderItems
- OrderStatusHistory

### Payment service

Database: PostgreSQL

Data:

- Payments
- PaymentTransactions
- PaymentStatus

### Notification service

Database: MongoDB

Data:

- Notifications
- NotificationTemplates
- DeliveryStatus

### Analytic service

Database: MongoDB

Data:

- Notifications
- NotificationTemplates
- DeliveryStatus

### Catching layer

Redis - Cache Aside Pattern

Product catalog cache
Session storage
Rate limiting
Frequently accessed queries

### Search engine

Product data is indexed into Elasticsearch to enable fast full-text search and filtering.

Example:

- search by name
- search by category
- search by tags

### Data synchronization

Data synchronization between services is handled through domain events.

Example:

- ProductCreated → indexed in Elasticsearch
- OrderCompleted → sent to Analytics Service

---

## 9. Security Architecture

The platform implements a layered security architecture to protect user data, system integrity, and service
communication.

Security is enforced at multiple levels including authentication, authorization, API gateway protection, and secure
communication between services.

### Authentication

Authentication is handled by the Auth Service using JWT-based authentication.

Users authenticate with their credentials and receive an access token that is used for subsequent requests.

Flow:

```text
User login
      │
      ▼
Auth Service
      │
      ▼
JWT token issued
      │
      ▼
Client sends token with requests
```

### Authorization

Model: Role-Based Access Control (RBAC)

System roles:

- ADMIN
- CUSTOMER
- WAREHOUSE_WORKER
- DATA_ANALYST

### JWT tokens

JWT tokens are used for stateless authentication across the system.
Tokens contain user identity and roles.

Payload:

```json
{
  "userId": "123",
  "roles": [
    "CUSTOMER"
  ],
  "exp": 1710000000
}
```

### API Gateway security

Spring cloud gateway protects API resources ensuring data secure, invalidating d2
attacks and adding secure filters.

Rate limit: 100 requests per minute per user (Using redis to manage containers).

Internal service communication can use secure network channels within the cluster using Kubernetes.

Sensitive configuration such as database credentials and API keys are stored securely using
environment variables or secret management systems.

---

## 10. Observability & Monitoring

Observability is critical in distributed systems to understand system behavior, detect failures, and analyze
performance.
The platform implements a monitoring stack that includes metrics collection, logging, and distributed tracing.

### Metrics collection

Prometheus collects metrics from all microservices.
Grafana provides dashboards to visualize system performance.

Metrics:

- API response time
- Request rate
- Error rate
- Order throughput
- Inventory updates

### Logging

Each microservice generates structured logs to capture important system events and errors.
Logs help developers debug issues and track system behavior.

Types:

- Application logs
- Error logs
- Security logs
- Event processing logs

### OpenTelemetry

Distributed tracing allows tracking a request as it flows across multiple services.
Each service adds tracing information to help visualize the entire request lifecycle.

### Health checks

/actuator/health

## 11. Deployment Architecture

The platform is deployed using containerized microservices to ensure scalability, portability and isolation between
services.

Containerization enables consistent environments across development, testing and production.

### Containerization: Docker

Each microservice is packaged as a Docker container.
Containers include the application code, dependencies, and runtime environment.

Examples:

- api-gateway
- auth-service
- user-service
- product-service
- inventory-service
- order-service
- payment-service
- notification-service
- analytics-service

### Local Development Environment

Local development uses Docker Compose to orchestrate all services and supporting infrastructure.

### CI/DC Pipeline: GitHub Actions

```text
Developer pushes code
        │
        ▼
CI Pipeline
        │
        ├── Run tests
        ├── Build application
        ├── Build Docker image
        └── Push image to container registry
```

### Container Orchestration

Kubernetes orchestrates containers and manages service deployment, scaling and fault recovery.

Responsibilities:

- Service discovery
- Load balancing
- Auto scaling
- Self-healing
- Rolling updates

### Infrastructure Components

|Component|Technology|
|API Gateway|Spring Cloud Gateway|
|Message Broker|Apache Kafka|
|Databases|PostgreSQL, MongoDB|
|Cache|Redis|
|Search|Elasticsearch|
|Monitoring|Prometheus + Grafana|

---

## 12. Architecture Decisions & Trade-offs

### Microservices vs Monolith

The system is designed using a microservices architecture instead of a monolithic architecture.
Microservices allow independent development, deployment and scaling of services.
They also enable clear domain boundaries.

Trade-offs:

- Increased system complexity
- Operational overhead
- More complex debugging

### Event-Driven Architecture

The system uses an event-driven architecture for inter-service communication.
Event-driven communication improves scalability and decoupling between services.
Services can react to events independently.

Trade-offs:

- Eventual consistency
- Harder debugging
- More complex system observability

### Saga Pattern

Ensures consistency across distributed transactions without using a global transaction manager.

Trade-offs:

- More complex workflow logic
- Compensating transactions required

### Containerization

Containers provide environment consistency and easier deployment.
Kubernetes enables scaling and fault tolerance.

Trade-offs:

- Infrastructure complexity
- Operational overhead
