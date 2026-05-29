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

# Clone the repository
git clone https://github.com/HajarBoulmane/ebanking-backend.git
cd ebanking-backend

# Update application.properties file (as shown above)
nano src/main/resources/application.properties

# Run the application
./mvnw spring-boot:run

Example:

spring.datasource.url=jdbc:mysql://localhost:3306/E-BANKING
spring.datasource.username=root
spring.datasource.password=
