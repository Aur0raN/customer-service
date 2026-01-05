# 🚀 Spring Boot Microservices Learning Project

## 📖 Project Overview

This is a learning project demonstrating microservices architecture using Spring Boot. The project consists of two independent services that communicate with each other to manage an inventory borrowing system.

The **Customer Service** manages customer information including their contact details, while the **Inventory Service** manages items that can be borrowed. The services communicate with each other through REST APIs, where the Inventory Service tracks which customer has borrowed which item through a `borrowedBy` field that references a customer ID.

---

## 🏗️ Architecture

This project implements a microservices architecture with the following components:

### 👥 Customer Service

- Manages customer data with fields: `id`, `customerName`, `customerEmail`, and `customerPhone`
- Uses its own PostgreSQL database for data persistence
- Exposes REST APIs for CRUD operations on customers

### 📦 Inventory Service

- Manages inventory items with fields: `id`, `name`, `description`, and `borrowedBy`
- The `borrowedBy` field stores the customer ID to track who borrowed the item
- Uses its own PostgreSQL database for data persistence
- Exposes REST APIs for CRUD operations on items

---

## 🔍 Service Discovery

The project uses **Spring Cloud Netflix Eureka** as a service registry. Both microservices register themselves with the Eureka server, allowing them to discover each other dynamically without hardcoded URLs.

---

## 🔗 Inter-Service Communication

**Spring Cloud OpenFeign** is used as the declarative REST client, enabling the Customer Service to call Inventory Service endpoints in a clean, interface-based approach rather than manually constructing HTTP requests.

---

## 💾 Database Architecture

Each service maintains its own isolated PostgreSQL database following the microservices principle of database per service:

- **Customer Service** → `customer_db`
- **Inventory Service** → `inventory_db`

They share data through the customer ID reference rather than direct database access.

---

## 🛠️ Key Technologies

| Technology | Purpose |
|------------|---------|
| **Spring Boot** | Framework for building the microservices |
| **Spring Data JPA** | Data persistence layer |
| **PostgreSQL** | Relational database for each service |
| **Lombok** | Reduces boilerplate code with annotations |
| **Spring Cloud Netflix Eureka** | Service discovery and registration |
| **Spring Cloud OpenFeign** | Declarative REST client for inter-service communication |

---

## 🎯 Learning Objectives

This architecture demonstrates fundamental microservices patterns including:

- ✅ Service isolation
- ✅ Independent data storage
- ✅ Service discovery
- ✅ Inter-service communication through REST APIs

---

## 📂 Project Structure

```
microservices-project/
├── customer-service/
│   └── src/main/java/com/shezan/customerservice/
│       ├── model/
│       ├── controller/
│       ├── service/
│       └── repository/
│
├── inventory-service/
│   └── src/main/java/com/shezan/inventoryservice/
│       ├── model/
│       ├── controller/
│       ├── service/
│       └── repository/
│
└── eureka-server/
```

---
## 📄 License

This project is for educational purposes.
