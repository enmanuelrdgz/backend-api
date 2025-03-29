# 🚀 Spring Boot Authentication API

This is a simple Spring Boot API that provides user authentication and registration endpoints.  
The API supports user registration, login, and retrieving authenticated user details.

## 📌 Features
- User registration (`/register`)
- User login (`/login`)
- Retrieve authenticated user details (`/me`)

---

## 🛠️ Technologies Used
- **Spring Boot** (REST API)
- **Spring Security** (Authentication & Authorization)
- **JWT** (JSON Web Tokens for authentication)
- **Spring Data JPA** (Database interaction)
- **PostgreSQL** (Configurable database)

---

## 📌 API Endpoints

### 🔹 **Register User**
**Endpoint:** `POST /register`  
Registers a new user.
#### **Request Body (JSON)**
```json
{
  "username": "john_doe",
  "password": "securepassword"
}
