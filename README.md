# Fondation Sociale Backend - Microservices Architecture

A comprehensive microservices architecture for a social foundation backend built with Spring Boot and Spring Cloud.

## Architecture Overview

This project implements a complete microservices architecture with the following services:

### Core Infrastructure Services
- **config-service** (Port 8888): Spring Cloud Config Server for centralized configuration
- **discovery-service** (Port 8761): Eureka Server for service discovery
- **api-gateway** (Port 8080): Spring Cloud Gateway for API routing

### Business Services
- **user-service** (Port 8081): User management and role-based access control
- **auth-service** (Port 8082): Authentication and authorization with JWT tokens
- **content-service** (Port 8083): Content management and categorization
- **social-service** (Port 8084): Social media features (posts, comments, likes)
- **notification-service** (Port 8085): Notification management
- **admin-service** (Port 8086): Administrative operations

## Technology Stack

- **Framework**: Spring Boot 2.7.18
- **Cloud**: Spring Cloud 2021.0.8
- **Database**: MySQL 8.0
- **Security**: Spring Security with JWT
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Inter-service Communication**: OpenFeign
- **Containerization**: Docker
- **Build Tool**: Maven

## Project Structure

Each microservice follows a consistent structure:

```
src/main/java/com.{servicename}/
├── config/              # Configuration classes
├── controller/          # REST controllers
├── dto/                 # Data Transfer Objects
│   ├── get/            # DTOs for GET operations
│   ├── post/           # DTOs for POST/PUT operations
│   └── Security/       # Security-related DTOs
├── entity/             # JPA entities
├── exception/          # Custom exception classes
├── repository/         # JPA repositories
├── service/            # Service layer
│   ├── facade/         # Service interfaces
│   └── Impl/           # Service implementations
├── Transformer/        # Entity/DTO transformers
└── {ServiceName}Application.java

src/main/resources/
├── static/             # Static resources
├── templates/          # Template files
└── application.properties
```

## Key Features

### User Management
- Complete CRUD operations for users and roles
- Role-based access control (ADMIN, ADHERENT)
- User activation/deactivation
- Profile management

### Authentication & Authorization
- JWT-based authentication
- Access and refresh tokens
- Token revocation and validation
- Account lockout on failed attempts

### Content Management
- Content creation and publishing
- Category management
- Content status workflow (DRAFT, PUBLISHED, ARCHIVED)
- View tracking

### Social Features
- Post creation and management
- Comments and likes system
- User interactions

### Notification System
- Multi-channel notifications
- Notification preferences
- Real-time delivery

### Administration
- Administrative dashboard
- System monitoring
- User management

## Database Schema

Each service has its own dedicated database:
- `fondation_user_db`: User and role management
- `fondation_auth_db`: Authentication tokens and sessions
- `fondation_content_db`: Content and categories
- `fondation_social_db`: Social interactions
- `fondation_notification_db`: Notifications
- `fondation_admin_db`: Administrative data

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0
- Docker (optional)

### Running with Maven

1. **Start Infrastructure Services** (in order):
   ```bash
   # Start Config Service
   cd config-service
   mvn spring-boot:run
   
   # Start Discovery Service
   cd ../discovery-service
   mvn spring-boot:run
   
   # Start API Gateway
   cd ../api-gateway
   mvn spring-boot:run
   ```

2. **Start Business Services**:
   ```bash
   # User Service
   cd user-service
   mvn spring-boot:run
   
   # Auth Service
   cd ../auth-service
   mvn spring-boot:run
   
   # Content Service
   cd ../content-service
   mvn spring-boot:run
   
   # Social Service
   cd ../social-service
   mvn spring-boot:run
   
   # Notification Service
   cd ../notification-service
   mvn spring-boot:run
   
   # Admin Service
   cd ../admin-service
   mvn spring-boot:run
   ```

### Running with Docker

1. **Build all services**:
   ```bash
   mvn clean package -DskipTests
   ```

2. **Build Docker images**:
   ```bash
   # Build individual service images
   docker build -t fondation/config-service config-service/
   docker build -t fondation/discovery-service discovery-service/
   docker build -t fondation/api-gateway api-gateway/
   docker build -t fondation/user-service user-service/
   docker build -t fondation/auth-service auth-service/
   docker build -t fondation/content-service content-service/
   docker build -t fondation/social-service social-service/
   docker build -t fondation/notification-service notification-service/
   docker build -t fondation/admin-service admin-service/
   ```

3. **Run with Docker Compose** (create docker-compose.yml):
   ```yaml
   version: '3.8'
   services:
     mysql:
       image: mysql:8.0
       environment:
         MYSQL_ROOT_PASSWORD: password
       ports:
         - "3306:3306"
     
     config-service:
       image: fondation/config-service
       ports:
         - "8888:8888"
     
     discovery-service:
       image: fondation/discovery-service
       ports:
         - "8761:8761"
       depends_on:
         - config-service
     
     # Add other services...
   ```

## API Documentation

### API Gateway Routes
- **User Service**: `http://localhost:8080/api/users/**`
- **Auth Service**: `http://localhost:8080/api/auth/**`
- **Content Service**: `http://localhost:8080/api/contents/**`
- **Social Service**: `http://localhost:8080/api/social/**`
- **Notification Service**: `http://localhost:8080/api/notifications/**`
- **Admin Service**: `http://localhost:8080/api/admin/**`

### Key Endpoints

#### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh JWT token
- `POST /api/auth/logout` - User logout

#### User Management
- `GET /api/users` - List all users
- `POST /api/users` - Create new user
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

#### Content Management
- `GET /api/contents` - List all content
- `POST /api/contents` - Create new content
- `GET /api/contents/{id}` - Get content by ID
- `PUT /api/contents/{id}` - Update content

## Monitoring & Health Checks

All services expose actuator endpoints:
- Health: `http://localhost:{port}/actuator/health`
- Info: `http://localhost:{port}/actuator/info`
- Metrics: `http://localhost:{port}/actuator/metrics`

Eureka Dashboard: `http://localhost:8761`

## Security

- JWT-based authentication across all services
- Role-based authorization (ADMIN, ADHERENT)
- CORS configuration for cross-origin requests
- Password encryption using BCrypt
- Account lockout for security

## Development

### Building the Project
```bash
mvn clean compile
```

### Running Tests
```bash
mvn test
```

### Packaging
```bash
mvn clean package
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, email support@fondationsociale.com or create an issue in this repository.