# E-Banking Backend

A secure backend API for a digital banking application built with Spring Boot.  
It provides RESTful services for account management, transactions, authentication, and banking operations.

## 🚀 Tech Stack
- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- MySQL
- Maven

## 📁 Project Structure

```bash
ebanking-backend/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/
│   │   │       ├── entities/
│   │   │       ├── repositories/
│   │   │       ├── services/
│   │   │       ├── web/
│   │   │       ├── dto/
│   │   │       └── security/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│
├── pom.xml
└── README.md
```
# Features
JWT authentication & authorization
Customer management
Bank account management
Debit / Credit / Transfer operations
REST API architecture
Exception handling
Secure endpoints
# Installation
git clone https://github.com/HajarBoulmane/ebanking-backend.git

cd ebanking-backend

Run the project:

./mvnw spring-boot:run

or

mvn spring-boot:run

API runs on:

http://localhost:8085
🗄️ Database Configuration

Update database credentials inside:

src/main/resources/application.properties

Example:

spring.datasource.url=jdbc:mysql://localhost:3306/E-BANKING
spring.datasource.username=root
spring.datasource.password=
