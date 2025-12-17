# 🎵 Greater Events – REST API for Music Event Management

> Academic Project – Distributed and Concurrent Programming 2025 – UNNOBA  
> A secure, layered Spring Boot backend for managing music events, artists, and user interactions with authentication and real-time notifications.

## 📌 Overview

**Greater Events** is a Spring Boot–based REST API designed to manage music events, artists, and end users. The system supports three distinct user roles:

- **Administrators**: fully manage artists and events throughout their lifecycle—from *tentative* to *confirmed*, *rescheduled*, or *canceled*.
- **Registered users**: can follow artists, mark events as favorites, and receive automatic notifications about relevant changes.
- **Public users**: access verified, read-only information about active artists and upcoming events (only those in *confirmed* or *rescheduled* states).

The application enforces strict business rules (e.g., only *tentative* events can be modified; past dates are not allowed) and secures sensitive operations using **JWT-based authentication**.

## ✨ Key Features

- Full lifecycle management of **artists** and **events**, with state validation (`TENTATIVE`, `CONFIRMED`, `RESCHEDULED`, `CANCELLED`).
- **User registration and login** with secure credential handling.
- **User engagement features**:
  - Follow or unfollow artists.
  - Add or remove events from favorites.
  - View upcoming events from followed artists (sorted by date).
- **Automatic notifications** triggered when:
  - A followed or favorited event is confirmed, rescheduled, or canceled.
- **Public endpoints** (no authentication required) to:
  - List all active artists.
  - Fetch upcoming events for a specific artist.
  - Retrieve full details of confirmed or rescheduled events.
- **Data protection**: events in *tentative* state are **never exposed** through public endpoints.

## 🛠️ Technologies Used

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Web** & **Spring Data JPA**
- **Hibernate** (JPA implementation)
- **H2** (in-memory, for development) / **PostgreSQL** (production-ready, configurable)
- **JWT** (JSON Web Token) via `com.auth0:java-jwt`
- **ModelMapper** (for DTO ↔ Entity mapping)
- **Lombok** (optional, to reduce boilerplate code)
- **Maven** (dependency and build management)

## 🔐 Authentication & Authorization

- JWT signed with **HMAC512** algorithm.
- Token expiration: **10 days**.
- Token format in responses and request headers:  
  ```http
  Authorization: Bearer <jwt_token>
  ```
- Role-based access:
  - `/admin/**`: admin-only (JWT-protected).
  - `/api/users/**`: authenticated end users (JWT-protected).
  - `/api/public/**`: open to all.

## 🧪 Running the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/greater-events.git
   ```

2. Configure your database in `application.properties` (H2 is used by default).

3. Build and run with Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

4. The API will be available at:  
   `http://localhost:8080`

## 📝 License

This project is for **academic purposes only** and is part of the coursework for **Distributed and Concurrent Programming 2025** at the **National University of Northwestern Buenos Aires (UNNOBA)**.
