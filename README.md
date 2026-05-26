# Classic Business Model Enterprise Application

[![Spring Boot](https://img.shields.io/badge/Framework-Spring%20Boot%203.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Thymeleaf](https://img.shields.io/badge/UI%20Engine-Thymeleaf%203-darkgreen.svg)](https://www.thymeleaf.org)
[![MySQL](https://img.shields.io/badge/Database-MySQL%208.0-blue.svg)](https://www.mysql.com/)
[![Vault](https://img.shields.io/badge/Security-HashiCorp%20Vault-black.svg)](https://www.vaultproject.io/)
[![Docker](https://img.shields.io/badge/Containerized-Docker-blue.svg)](https://www.docker.com/)
[![AWS EC2](https://img.shields.io/badge/Deployment-AWS%20EC2-orange.svg)](https://aws.amazon.com/ec2/)
[![Deployment Platform](https://img.shields.io/badge/Production%20IP-13.60.34.150%3A8080-orange.svg)](http://13.60.34.150:8080/)

---

# 📌 Project Description

Classic Business Model Enterprise Application is an enterprise-grade ERP and Supply Chain Management backend system developed using Spring Boot architecture. The application manages organizational operations including employees, offices, customers, products, orders, payments, and analytical business reports.

The system follows a layered enterprise architecture with secure secret management, RESTful APIs, centralized database integration, Docker containerization, and AWS cloud deployment.

The platform provides:

- RESTful APIs for enterprise operations
- Secure authentication & authorization
- Business intelligence reports
- Thymeleaf-based frontend views
- Dockerized deployment
- AWS EC2 hosting
- HashiCorp Vault integration for secret management
- MySQL relational database support

---

# 🌐 Deployed Application

## Production Deployment URL

### Main Application
http://13.60.34.150:8080

### Swagger API Documentation
http://13.60.34.150:8080/swagger-ui/index.html

---

# 🏗️ Technical Architecture

The system follows a layered enterprise architecture:

```text
Client / Browser
        │
        ▼
REST Controllers / Thymeleaf Views
        │
        ▼
Service Layer (Business Logic)
        │
        ▼
Repository Layer (JPA / Hibernate)
        │
        ▼
MySQL Database
```
# ⚙️ Technologies Used

## Backend
- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- JWT Authentication

## Frontend
- Thymeleaf
- HTML5
- CSS3

## Database
- MySQL

## Security
- HashiCorp Vault

## DevOps & Deployment
- Docker
- AWS EC2
- Maven

## Documentation & Testing
- Swagger OpenAPI

---

# 🛠️ Features Implemented

- CRUD operations for enterprise entities
- Employee hierarchy management
- Office management system
- Customer management
- Product catalog system
- Order management pipeline
- Analytical reporting APIs
- Role-based endpoint access
- Vault secret management
- Docker containerization
- AWS deployment
- Swagger API documentation

---

# 👥 Team Members & Work Distribution

---

## 🧑‍💻 Aryan Baranwal

### Modules Handled
- Office Module
- Report Module
- AWS Deployment
- Centralized Database
- Swagger Testing & Integration

### Office Endpoints
- `GET /api/offices`
- `GET /api/offices/{officeCode}`
- `GET /api/offices/{officeCode}/employees`

### Report Endpoints
- `GET /api/reports/sales-by-employee`
- `GET /api/reports/sales-by-country`
- `GET /api/reports/customer-exposure`
- `GET /api/reports/order-value/{orderNumber}`

### Additional Responsibilities
- AWS EC2 deployment
- Swagger testing
- Security integration coordination
- Endpoint validations
- API response optimization

---

## 🧑‍💻 Aman Raj

### Modules Handled
- Employee Module
- Thymeleaf Frontend Integration

### Employee Endpoints
- `GET /api/employees/{employeeNumber}`
- `PUT /api/employees/{employeeNumber}`
- `DELETE /api/employees/{employeeNumber}`
- `GET /api/employees`
- `POST /api/employees`
- `GET /api/employees/{employeeNumber}/subordinates`
- `GET /api/employees/{employeeNumber}/manager`

### Thymeleaf Views
- Landing Page UI
- Dashboard UI
- Office View Templates
- Dynamic HTML Rendering
- Dockerization

---

## 🧑‍💻 Gaddam Kushal Kumar Reddy

### Modules Handled
- Customer Management Module

### Customer Endpoints
- `GET /api/customers/{customerNumber}`
- `PUT /api/customers/{customerNumber}`
- `DELETE /api/customers/{customerNumber}`
- `GET /api/customers/{customerNumber}/credit-limit`
- `PUT /api/customers/{customerNumber}/credit-limit`
- `GET /api/customers`
- `POST /api/customers`
- `GET /api/customers/search`


---

## 👩‍💻 Dhanavarshini

### Modules Handled
- Product Module
- Product Line Module
- Financial Reporting Module

### Product Endpoints
- `GET /api/products`
- `GET /api/products/{productCode}`
- `GET /api/product-lines`
- `GET /api/product-lines/{productLine}/products`

### Financial Report Endpoints
- `GET /api/reports/monthly-revenue`
- `GET /api/reports/high-risk-customers`

---

## 👩‍💻 Shivani S.

### Modules Handled
- Order Management Module
- HashiCorp Vault Integration
- Security Configuration

### Order Controller Endpoints
- `GET /api/orders/{orderNumber}`
- `PUT /api/orders/{orderNumber}`
- `GET /api/orders`
- `POST /api/orders`
- `PATCH /api/orders/{orderNumber}/status`
- `GET /api/orders/search`
- `GET /api/customers/{customerNumber}/orders`

### Order Detail Controller Endpoints
- `PUT /api/orders/{orderNumber}/items/{productCode}`
- `DELETE /api/orders/{orderNumber}/items/{productCode}`
- `GET /api/orders/{orderNumber}/items`
- `POST /api/orders/{orderNumber}/items`

### Security Responsibilities
- Vault integration
- Secret management
- Environment configuration

---

# 🔐 Authentication & Authorization

The application uses:

- Spring Security
- Role-based Authorization
- Protected REST Endpoints

Each team member was assigned role-specific access to their endpoints during testing and integration.

---

# 🗄️ Database Design

The project uses the ClassicModels relational database schema with the following tables:

- offices
- employees
- customers
- payments
- productlines
- products
- orders
- orderdetails

The schema is fully normalized and connected using:
- Primary Keys
- Foreign Keys
- Self-referencing relationships
- Composite Keys

---

# 🐳 Docker Deployment

The application was containerized using Docker.

## Docker Features
- Multi-stage Docker build
- Portable runtime environment
- Production-ready container setup
- Environment-variable based configuration

---

# ☁️ AWS Deployment

The application is deployed on AWS EC2.

## Deployment Components
- AWS EC2 Instance
- Docker Container
- MySQL Database
- Vault Integration
- Public API Access

---

# ▶️ How to Run the Project Locally

---

## 1️⃣ Clone Repository

```bash
git clone <repository-url>
cd classic-business-model
```

---

## 2️⃣ Configure Environment Variables

Set the following environment variables:

```bash
VAULT_TOKEN=your_vault_token
DB_URL=jdbc:mysql://localhost:3306/classicmodels
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

---

## 3️⃣ Run Using Maven

```bash
mvn spring-boot:run
```

---

## 4️⃣ Run Using JAR

```bash
mvn clean package
java -jar target/classicmodels-0.0.1-SNAPSHOT.jar
```

---

# 🐳 Run Using Docker Compose

## 1️⃣ Create `.env` File

Create a `.env` file in the project root directory and add the following variables:

```env
VAULT_TOKEN=your_vault_token
DB_URL=jdbc:mysql://your-db-host:3306/classicmodels
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

---

## 2️⃣ Run Using Docker Compose

```bash
docker compose up --build
```

---

## 3️⃣ Stop Containers

```bash
docker compose down
```

---

# ☁️ Docker Compose Features

- Multi-container support
- Environment-based configuration
- Simplified deployment
- Centralized service management
- Portable development & production setup

# 📖 API Documentation

Swagger UI:

```text
http://13.60.34.150:8080/swagger-ui/index.html
```

---

# 🧪 Testing

The project was tested using:
- Swagger UI
- Unit Testing
- Endpoint Validation

---

# 📂 Project Structure

```text
src/main/java/com/classicmodels
│
├── controller
├── service
├── repository
├── entity
├── dto
├── config
├── security
└── exception
```

---

# 🧠 Design Principles Used

The project follows several important software engineering principles:

- Layered Architecture
- Separation of Concerns
- Dependency Injection
- Inversion of Control
- Repository Pattern
- DTO Pattern
- RESTful API Design
- SOLID Principles
- Stateless Authentication
- Externalized Configuration

---

# 📌 Contributors

- Aryan Baranwal (Admin)
- Aman Raj
- Gaddam Kushal Kumar Reddy
- Dhanavarshini 
- Shivani S.
