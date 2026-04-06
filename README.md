# 💰 Finance Dashboard Backend

A role-based backend system for managing financial records and generating dashboard insights.
Built using Spring Boot, this project demonstrates clean architecture, REST API design, and practical backend engineering concepts.

---

## 🚀 Features

### 👤 User & Role Management

* Create and manage users
* Assign roles:

  * **VIEWER** → Can only view dashboard
  * **ANALYST** → Can view records and insights
  * **ADMIN** → Full access (CRUD + user management)
* Manage user status (ACTIVE / INACTIVE)

---

### 💰 Financial Records Management

* Create financial records (income / expense)
* View records
* Update records
* Soft delete records
* Associate records with users

Each record includes:

* Amount (BigDecimal for precision)
* Type (INCOME / EXPENSE)
* Category
* Date
* Notes

---

### 📊 Dashboard APIs

Provides aggregated insights:

* Total income
* Total expenses
* Net balance

---

### 🔐 Role-Based Access Control

Access is enforced at the service layer:

| Role    | Dashboard | View Records | Create | Update | Delete |
| ------- | --------- | ------------ | ------ | ------ | ------ |
| Viewer  | ✅         | ❌            | ❌      | ❌      | ❌      |
| Analyst | ✅         | ✅            | ❌      | ❌      | ❌      |
| Admin   | ✅         | ✅            | ✅      | ✅      | ✅      |

---

### 🔍 Filtering Support

Records can be filtered by:

* Date range
* Type (INCOME / EXPENSE)
* Category

---

### ⚠️ Global Exception Handling

* Centralized error handling using `@RestControllerAdvice`
* Consistent error responses with status and timestamp

---

### 🌱 Data Seeding

* Flyway migrations used
* Initial users and records are seeded automatically

---

### 📘 API Documentation

* Swagger UI available for testing APIs
* Interactive API exploration

---

## 🛠️ Tech Stack

* Java 17
* Spring Boot 3
* Spring Data JPA (Hibernate)
* PostgreSQL
* Flyway (Database migrations)
* Swagger (springdoc-openapi)
* Lombok

---

## ⚙️ Project Structure

```
controller/     → REST APIs
service/        → Business logic
repository/     → Database access
entity/         → JPA entities
exception/      → Global error handling
```

---

## ▶️ Getting Started

### 1. Clone the repository

```
git clone https://github.com/your-username/finance-dashboard.git
cd finance-dashboard
```

---

### 2. Set Environment Variables

```
DB_URL=jdbc:postgresql://localhost:5432/finance_db
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

---

### 3. Run the application

```
mvn spring-boot:run
```

---

### 4. Access Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🧪 How to Test

1. Get users:

```
GET /users
```

2. Copy any `userId`

3. Use it in APIs:

```
GET /records?userId=...
POST /records/{userId}
GET /dashboard?userId=...
```

---

## 📌 Sample Requests

### Create User

```json
{
  "name": "Tushar",
  "email": "tushar@test.com"
}
```

---

### Create Record

```json
{
  "amount": 5000.00,
  "type": "INCOME",
  "category": "Salary",
  "recordDate": "2026-04-01",
  "notes": "Monthly salary"
}
```

---

## 🧠 Design Decisions

* **UUID used** for unique identifiers
* **BigDecimal** used for financial accuracy
* **Soft delete** implemented for records
* **Service-layer access control** instead of full security setup (as per assignment scope)
* **Environment variables** used for secure configuration

---

## ⚠️ Assumptions

* Authentication is not implemented
* `userId` is passed manually to simulate user context
* System is designed for demonstration, not production

---

## 🚀 Future Improvements

* Add authentication (JWT / OAuth)
* Implement role-based authorization using Spring Security
* Add pagination and sorting
* Introduce DTO layer for better API design
* Add unit and integration tests
* Implement caching for dashboard APIs
* Add audit logs for record changes

---

## 👨‍💻 Author

Tushar Kumar

---

## 📄 License

This project is for educational and assessment purposes.
