# 🧾 Order Service - Production Ready Event-Driven System

## 🚀 Overview

This project is a **production-grade Order Management Service** built using modern backend practices and patterns.

It follows an **Event-Driven Microservices Architecture** using:
- **Spring Boot**
- **Apache Kafka**
- **MongoDB**
- **Transactional Outbox Pattern**

The system ensures:
- ✅ High scalability
- ✅ Fault tolerance
- ✅ Data consistency
- ✅ Reliable message processing

## Localhost Access
- Order Service: `http://localhost:9090/swagger-ui/index.html`
- Kafka: `http://localhost:9092`
- MongoDB: `mongodb://localhost:27017`
---

## 🏗️ Architecture
The architecture consists of the following components:
1. Client places order → Order Service
2. Order saved in MongoDB (Orders + Outbox)
3. Scheduler publishes event to Kafka
4. Multiple services consume event:
    - Inventory Service → reserves stock
    - Payment Service → processes payment
    - Notification Service → sends alerts
5. Services emit events back to Kafka
6. Order Service consumes events and updates final status
