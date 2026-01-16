# 🚗 RoadReady – Car Rental System

RoadReady is a **web-based Car Rental Management System** that allows customers to search, book, and manage rental vehicles online. The platform also provides administrative tools for managing vehicles, reservations, users, and pricing, ensuring a smooth and secure rental experience.

This project is developed following **enterprise application standards** and **Hexaware Technologies project guidelines**.

---


## 🎯 Scope of the Project

- User registration and secure authentication  
- Browse and search available cars  
- Vehicle reservation with pickup & drop-off scheduling  
- Booking management (view, modify, cancel)  
- Secure online payment processing  
- Ratings and reviews for rental experience  
- Admin dashboard for fleet and booking management  

---

## 🧩 Actors & Use Cases

### 👤 Customer
- Register and login
- Browse available vehicles
- Make a reservation
- View or cancel bookings
- View rental history
- Submit ratings and reviews

### 🧑‍💼 Admin / Rental Manager
- Manage vehicles and brands
- Manage customer accounts
- View and manage reservations
- Configure pricing and promotions
- Generate reports and analytics

### 🧑‍🔧 Rental Agent / Employee
- Vehicle check-in & check-out
- Update vehicle availability
- Manage vehicle inventory
- Report maintenance issues

---

## 🛠️ Technologies Used

| Layer | Technology |
|------|-----------|
| Frontend | React.js  |
| Backend | Java (Spring Boot) |
| Database | MySQL |
| Authentication | JWT (JSON Web Tokens) |


---

## 🔐 Security & Authentication

### JWT Authentication
- JWT token generated after successful login
- Token contains user ID, role, and expiration
- Token is signed using a secure server-side key

### Authorization
- Role-based access control (Customer / Admin / Agent)
- Protected APIs require a valid JWT token

### Logout
- Token invalidation to prevent unauthorized access

---

## ⚙️ Application Modules

### Customer Module
- Vehicle search and booking
- Reservation management
- Payment and billing history
- Ratings and feedback

### Admin Module
- Vehicle and fleet management
- User management
- Booking and revenue reports
- Pricing and availability control

### Rental Agent Module
- Vehicle check-in / check-out
- Inventory updates
- Maintenance alerts

---

## ▶️ How to Run the Car Rental System

### ✅ Prerequisites

- Java JDK 11+
- Maven
- MySQL
- React.js & npm
- IDE (IntelliJ / Eclipse / VS Code)
- Git

---

### 📥 Step 1: Clone the Repository

```bash
git clone https://github.com/Arthic17/java-fsd-hex-may-25.git
cd "java-fsd-hex-may-25/Car Rental System"
```
### ⚙️ Step 2: Backend Setup (Spring Boot)

## Update application.properties:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/carrental
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Create database:
```bash
CREATE DATABASE carrental;
```

## Run backend:
```bash
mvn clean install
mvn spring-boot:run
```

## Backend URL:
```bash
http://localhost:8080
```

### 🎨 Step 3: Frontend Setup
```bash
cd frontend
npm install
npm start
```

Frontend URL:
```bash
http://localhost:3000
```
---
## 🧪 Testing

Use Postman

Test authentication, booking flow, and admin APIs

---

## 👩‍💻 Author

Vaishnavi Sharad Patil
Computer Science & Engineering (2025)
---
✨ RoadReady delivers a secure, scalable, and efficient car rental solution aligned with real-world industry requirements.
