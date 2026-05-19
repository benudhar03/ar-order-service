# 🧾 Order Service & 🛍️ Product Service - Production Ready Event-Driven Ecommerce System

## 🚀 Overview
This project contains two production-grade microservices designed for scalable ecommerce systems:

1. **Order Service**
2. **Product Service**


It follows scalable backend design principles using:

- **Spring Boot**
- **MongoDB**
- **MongoTemplate**
- **REST APIs**
- **Dynamic Filtering**
- **Pagination & Sorting**
- **Redis Caching (Extensible)**
- **Distributed Locking Ready Architecture**

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

---

# 🧩 Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Backend Development |
| Spring Boot | REST APIs |
| MongoDB | Product Catalog Database |
| MongoTemplate | Dynamic Querying |
| Maven | Build Tool |
| Lombok | Boilerplate Reduction |
| Swagger/OpenAPI | API Documentation |

---

## Request Payload Example
```json
{
   "vendorId": "VENDOR_1001",
   "vendorName": "ABC Mobiles Pvt Ltd",
   "productName": "iPhone 17 Pro",
   "skuCode": "APL-IP17PRO-256",
   "category": "Electronics",
   "subCategory": "Mobile",
   "brand": "Apple",
   "price": 159999,
   "quantity": 50,
   "description": "Latest Apple flagship mobile",
   "imageUrls": [
      "https://cdn.company.com/products/apple/front.png",
      "https://cdn.company.com/products/apple/back.png"
   ],
   "active": true,
   "attributes": {
      "color": "Black Titanium",
      "storage": "256GB",
      "ram": "12GB"
   }
}
```