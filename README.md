# Smart Hospital Management System

> **Summer Project by Team 10**  
> A modern hospital management system built with Spring Boot, providing comprehensive patient management, doctor workspace, appointment system, and more.

## Project Features

### Core Functionality
- **Patient Management** - Patient registration, profile management, health records
- **Doctor Workspace** - Patient diagnosis, prescription management, clinical notes
- **Appointment System** - Smart appointment scheduling, calendar view
- **Health Monitoring** - Vital signs recording, history tracking
- **Report System** - Patient report generation, PDF export
- **Health Q&A** - Doctor-patient interaction platform

### Security Features
- Spring Security authentication
- Role-based access control
- Password reset functionality

## Technology Stack

### Backend Technologies
- **Java 17** - Core programming language
- **Spring Boot 3.5.3** - Application framework
- **Spring Security** - Security framework
- **MyBatis 3.0.5** - Data persistence layer
- **MySQL** - Primary database
- **Lombok** - Code simplification

### Frontend Technologies
- **Thymeleaf** - Server-side template engine
- **HTML/CSS/JavaScript** - Frontend basics
- **Bootstrap** - UI component library

### Development Tools
- **Maven** - Project build tool
- **Spring Boot DevTools** - Hot reload
- **OpenPDF** - PDF generation
- **Flying Saucer** - HTML to PDF conversion

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

## Project Structure

```
src/
├── main/
│   ├── java/com/team10/smarthospital/
│   │   ├── controllers/     # Controller layer
│   │   ├── service/         # Business logic layer
│   │   ├── mapper/          # Data access layer
│   │   ├── model/           # Data models
│   │   ├── dto/             # Data transfer objects
│   │   ├── repository/      # Data repositories
│   │   └── api/             # API interfaces
│   └── resources/
│       ├── templates/       # Thymeleaf templates
│       ├── static/          # Static resources
│       ├── mapper/          # MyBatis mapping files
│       └── application.yml  # Configuration file
└── test/                    # Test code
```

## API Documentation

### Main Endpoints
- `GET /` - Homepage
- `GET /patient/registration` - Patient registration page
- `GET /dashboard` - Dashboard
- `GET /appointments` - Appointment management
- `GET /vitals` - Vital signs management

## Team Members

**Team 10** - Summer Project 2024

---

## What We Learned

This project helped us understand:
- Spring Boot application development
- Database design and management
- Web application security
- Team collaboration and version control
- Real-world software development practices

## Academic Context

This project was developed as part of our summer course requirements, demonstrating our understanding of:
- Object-oriented programming principles
- Web application architecture
- Database management systems
- Software engineering best practices

*Note: This is a learning project and may not be suitable for production use without additional security and performance considerations.*

