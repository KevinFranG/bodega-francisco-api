# Bodegas Francisco API - Architecture

Online store platform for distributing and selling products to customers across multiple regions.

---

## Table of Contents

1. [System Overview](#1-system-overview)
2. [System Actors](#2-system-actors)
3. [Main Use Cases](#3-main-use-cases)
4. [Domain Boundaries](#4-domain-boundaries)
5. [Microservices Architecture](#5-microservices-architecture)
6. [Event-Driven Architecture](#6-event-driven-architecture)
7. [System Lifecycle](#7-system-lifecycle)
8. [Data Architecture](#8-data-architecture)
9. [Security Architecture](#9-security-architecture)
10. [Observability and Monitoring](#10-observability-and-monitoring)
11. [Deployment Architecture](#11-deployment-architecture)
12. [Architecture Decisions and Trade-offs](#12-architecture-decisions-and-trade-offs)

---

## 1. System Overview

**Bodegas Francisco Commerce Platform S.A. de C.V.**

### Problem Statement

Bodegas Francisco is a regional distribution business that requires a scalable digital platform to manage product sales,
inventory, customer orders, credit purchases, and warehouse logistics across multiple regions.

### Goals

- Allow customers to browse and purchase products online
- Manage customer credit and loans
- Manage inventory across warehouses
- Process orders and payments reliably
- Provide analytics and business insights
- Support asynchronous integrations between services

### Non-Functional Goals

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

### Warehouse Worker

Responsible for preparing and shipping customer orders.

### Administrator

Manages products, categories, and system configurations.

### Data Analyst

Accesses analytics and reports to understand sales performance and business metrics.

---

## 3. Main Use Cases

### Customer

- Register and log in
- Browse the product catalog
- View product details
- Add products to cart
- Place an order
- Receive notifications about order status
- View order history
- Request credit purchases

### Warehouse Worker

- View pending orders
- Prepare products for shipment
- Update shipment status
- Check product stock

### Administrator

- Create and update products
- Manage categories
- Monitor inventory
- Manage users
- Create and update discounts
- Manage debtors

### Data Analyst

- View sales statistics
- Analyze top-selling products
- Analyze customer behavior

---

## 4. Domain Boundaries

### Identity and Access

- User authentication
- Token generation
- Role-based authorization
- Session management

### User Domain

- User profiles
- Addresses
- Customer information

### Product Catalog Domain

- Product information
- Categories
- Product descriptions
- Product search

### Inventory Domain

- Product stock levels
- Inventory reservations
- Stock movements
- Warehouse visibility

### Order Domain

- Order creation
- Order lifecycle
- Order history
- Order cancellation

### Payment Domain

- Payment requests
- Payment confirmation
- Payment failures

### Notification Domain

- Email notifications
- WebSocket notifications
- System alerts

### Analytics Domain

- Sales metrics
- Product performance
- Customer insights
- Business dashboards

---

## 5. Microservices Architecture

### API Gateway

The API Gateway is the single entry point for all client requests. It handles routing, authentication validation, rate
limiting, and request forwarding to the appropriate microservices.

**Responsibilities**

- Request routing
- JWT validation
- Rate limiting
- API aggregation

### Auth Service

Responsible for user authentication and authorization. It manages login, token generation, and session validation.

**Responsibilities**

- User login
- Token generation
- Token validation
- OAuth integration

**Technologies**

- JWT
- OAuth2
- Keycloak

### User Service

Manages user profiles and customer information.

**Responsibilities**

- User profiles
- Addresses
- Customer preferences
- User purchases

### Product Catalog Service

Responsible for managing product data and categories.

**Responsibilities**

- Product creation
- Product updates
- Product listing
- Product search

**Technologies**

- Elasticsearch for product queries

### Inventory Service

Handles stock levels and product availability.

**Responsibilities**

- Track product stock
- Reserve inventory
- Release inventory

### Order Service

Manages customer orders and the order lifecycle.

**Responsibilities**

- Create orders
- Cancel orders
- Track order status
- Maintain order history

### Payment Service

Processes payment requests and payment confirmations.

**Responsibilities**

- Process payments
- Handle payment failures
- Payment webhooks

### Notification Service

Responsible for sending notifications to users.

**Types**

- Email notifications
- WebSocket notifications
- System alerts

### Analytics Service

Processes business events and generates metrics and reports.

**Responsibilities**

- Sales analytics
- Product performance metrics
- Customer insights

### Service Communication

The system uses a hybrid communication model.

**Synchronous communication**

- REST APIs
- GraphQL queries

**Asynchronous communication**

- Event-driven messaging using Apache Kafka

---

## 6. Event-Driven Architecture

The platform uses an event-driven architecture to allow services to communicate asynchronously. This approach improves
scalability, resilience, and decoupling between services.

Events are published to a message broker and consumed by interested services.

### Event Broker

The system uses Apache Kafka as the event streaming platform. Kafka allows services to publish and consume events
without tight coupling.

### Order Creation Flow

1. Customer places an order
2. Order Service creates the order
3. Order Service publishes an `OrderCreated` event
4. Multiple services consume the event

**Services involved**

1. Inventory Service reserves stock
2. Payment Service processes payment
3. Notification Service notifies the user
4. Analytics Service records the sale

### Core System Events

| Event               | Consumers                                                                   |
|---------------------|-----------------------------------------------------------------------------|
| `UserRegistered`    | Notification Service, Analytics Service                                     |
| `ProductCreated`    | Product Service, Analytics Service, Search Index                            |
| `ProductUpdated`    | Product Service, Search Index                                               |
| `ProductOutOfStock` | Notification Service, Analytics Service                                     |
| `CartCheckedOut`    | Order Service                                                               |
| `OrderConfirmed`    | Notification Service, Analytics Service                                     |
| `OrderCreated`      | Inventory Service, Payment Service, Notification Service, Analytics Service |
| `OrderCancelled`    | Inventory Service, Notification Service, Analytics Service                  |
| `OrderCompleted`    | Notification Service, Analytics Service                                     |
| `PaymentRequested`  | Payment Service                                                             |
| `PaymentCompleted`  | Order Service, Notification Service, Analytics Service                      |
| `PaymentFailed`     | Order Service, Notification Service, Analytics Service                      |
| `InventoryReserved` | Order Service                                                               |
| `InventoryReleased` | Order Service                                                               |
| `OrderShipped`      | Notification Service, Analytics Service                                     |
| `OrderDelivered`    | Notification Service, Analytics Service                                     |
| `CartCreated`       | User Service                                                                |
| `CartItemAdded`     | User Service                                                                |
| `CartItemRemoved`   | User Service                                                                |

### Example Event Payload

`OrderCreated`

```json
{
  "orderId": "12345",
  "customerId": "67890",
  "items": [
    {
      "productId": "ABC-001",
      "quantity": 2
    }
  ],
  "total": 950.00,
  "createdAt": "2026-03-27T12:00:00Z"
}
```

---

## 7. System Lifecycle

### Order States

| State             | Description                                                        |
|-------------------|--------------------------------------------------------------------|
| `CREATED`         | The order has been created but payment has not yet been processed. |
| `PENDING_PAYMENT` | The payment request has been initiated.                            |
| `PAID`            | Payment was successful.                                            |
| `PROCESSING`      | The warehouse is preparing the order.                              |
| `SHIPPED`         | The order has been sent to the customer.                           |
| `DELIVERED`       | The order has reached the customer.                                |
| `CANCELLED`       | The order was cancelled by the user or system.                     |
| `FAILED`          | The order failed due to payment or inventory issues.               |

### Main Flow

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

### Failure Scenarios

**Payment failure**

```text
PaymentFailed
        │
        ▼
OrderCancelled
        │
        ▼
InventoryReleased
```

**Inventory failure**

```text
InventoryUnavailable
        │
        ▼
OrderCancelled
```

### Saga Pattern

The order workflow is implemented using the Saga Pattern to maintain data consistency across multiple services. Each
service performs a local transaction and publishes an event that triggers the next step in the workflow.

#### Compensating Transactions

If any step fails, compensating actions are triggered to revert previous operations.

| Step              | Action              | Compensation       |
|-------------------|---------------------|--------------------|
| Create Order      | `OrderCreated`      | `CancelOrder`      |
| Reserve Inventory | `InventoryReserved` | `ReleaseInventory` |
| Process Payment   | `PaymentCompleted`  | `RefundPayment`    |

There are two main approaches to implementing the Saga Pattern:

1. **Orchestration**: a central service coordinates the workflow.
2. **Choreography**: each service reacts to events and triggers the next step.

This platform uses a choreography-based saga approach built on Kafka event streaming.

---

## 8. Data Architecture

Each microservice owns its data and manages its own database. Services must not access another service's database
directly.

Data is shared across services through APIs or events.

### Auth Service

**Database:** PostgreSQL

**Data**

- User credentials
- Tokens
- Roles

### User Service

**Database:** MongoDB

**Data**

- User profiles
- Addresses
- Customer preferences

### Product Catalog Service

**Database:** PostgreSQL

**Data**

- Products
- Categories
- Product images

### Inventory Service

**Database:** PostgreSQL

**Data**

- Inventory
- Stock movements
- Reservations

### Order Service

**Database:** PostgreSQL

**Data**

- Orders
- Order items
- Order status history

### Payment Service

**Database:** PostgreSQL

**Data**

- Payments
- Payment transactions
- Payment status

### Notification Service

**Database:** MongoDB

**Data**

- Notifications
- Notification templates
- Delivery status

### Analytics Service

**Database:** MongoDB

**Data**

- Sales metrics
- Product performance data
- Customer behavior insights

### Caching Layer

**Redis - Cache Aside Pattern**

- Product catalog cache
- Session storage
- Rate limiting
- Frequently accessed queries

### Search Engine

Product data is indexed into Elasticsearch to enable fast full-text search and filtering.

**Examples**

- Search by name
- Search by category
- Search by tags

### Data Synchronization

Data synchronization between services is handled through domain events.

**Examples**

- `ProductCreated` → indexed in Elasticsearch
- `OrderCompleted` → sent to Analytics Service

---

## 9. Security Architecture

The platform implements a layered security architecture to protect user data, system integrity, and service
communication.

Security is enforced at multiple levels, including authentication, authorization, API Gateway protection, and secure
communication between services.

### Authentication

Authentication is handled by the Auth Service using JWT-based authentication.

Users authenticate with their credentials and receive an access token that is used for subsequent requests.

**Flow**

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

**Model:** Role-Based Access Control (RBAC)

**System roles**

- `ADMIN`
- `CUSTOMER`
- `WAREHOUSE_WORKER`
- `DATA_ANALYST`

### JWT Tokens

JWT tokens are used for stateless authentication across the system. Tokens contain user identity and roles.

**Payload**

```json
{
  "userId": "123",
  "roles": [
    "CUSTOMER"
  ],
  "exp": 1710000000
}
```

### API Gateway Security

Spring Cloud Gateway protects API resources by validating requests, applying security filters, and helping mitigate
abuse scenarios.

**Rate limit:** 100 requests per minute per user, using Redis.

Internal service communication can use secure network channels within the cluster, for example in Kubernetes.

Sensitive configuration such as database credentials and API keys should be stored using environment variables or secret
management systems.

---

## 10. Observability and Monitoring

Observability is critical in distributed systems to understand system behavior, detect failures, and analyze
performance.

The platform implements a monitoring stack that includes metrics collection, logging, and distributed tracing.

### Metrics Collection

Prometheus collects metrics from all microservices. Grafana provides dashboards to visualize system performance.

**Metrics**

- API response time
- Request rate
- Error rate
- Order throughput
- Inventory updates

### Logging

Each microservice generates structured logs to capture important system events and errors.

**Types**

- Application logs
- Error logs
- Security logs
- Event processing logs

### OpenTelemetry

Distributed tracing allows tracking a request as it flows across multiple services. Each service adds tracing
information to help visualize the full request lifecycle.

### Health Checks

```text
/actuator/health
```

---

## 11. Deployment Architecture

The platform is deployed using containerized microservices to ensure scalability, portability, and isolation between
services.

Containerization enables consistent environments across development, testing, and production.

### Containerization with Docker

Each microservice is packaged as a Docker container.

**Examples**

- API Gateway
- Auth Service
- User Service
- Product Service
- Inventory Service
- Order Service
- Payment Service
- Notification Service
- Analytics Service

### Local Development Environment

Local development uses Docker Compose to orchestrate all services and supporting infrastructure.

### CI/CD Pipeline with GitHub Actions

```text
Developer pushes code
        │
        ▼
CI pipeline
        │
        ├── Run tests
        ├── Build application
        ├── Build Docker image
        └── Push image to container registry
```

### Container Orchestration

Kubernetes orchestrates containers and manages deployment, scaling, and fault recovery.

**Responsibilities**

- Service discovery
- Load balancing
- Auto-scaling
- Self-healing
- Rolling updates

### Infrastructure Components

| Component      | Technology           |
|----------------|----------------------|
| API Gateway    | Spring Cloud Gateway |
| Message Broker | Apache Kafka         |
| Databases      | PostgreSQL, MongoDB  |
| Cache          | Redis                |
| Search         | Elasticsearch        |
| Monitoring     | Prometheus + Grafana |

---

## 12. Architecture Decisions and Trade-offs

### Microservices vs. Monolith

The system is designed using a microservices architecture instead of a monolithic architecture. Microservices allow
independent development, deployment, and scaling of services while enabling clear domain boundaries.

**Trade-offs**

- Increased system complexity
- Operational overhead
- More complex debugging

### Event-Driven Architecture

The system uses an event-driven architecture for inter-service communication. This improves scalability and decoupling
by allowing services to react independently to events.

**Trade-offs**

- Eventual consistency
- Harder debugging
- More complex observability

### Saga Pattern

The Saga Pattern ensures consistency across distributed transactions without using a global transaction manager.

**Trade-offs**

- More complex workflow logic
- Compensating transactions are required

### Containerization

Containers provide environment consistency and easier deployment. Kubernetes enables scaling and fault tolerance.

**Trade-offs**

- Infrastructure complexity
- Operational overhead
