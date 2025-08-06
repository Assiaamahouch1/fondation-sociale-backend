# Fondation Sociale Backend - Microservices Architecture

## Overview
This project is a complete microservices-based backend for a social foundation platform. It implements a distributed architecture using Spring Cloud technologies with proper service discovery, configuration management, and inter-service communication.

## Architecture

### Microservices
1. **config-service** (Port 8888) - Configuration Server
2. **discovery-service** (Port 8761) - Service Discovery (Eureka)
3. **user-service** (Port 8081) - User Management
4. **auth-service** (Port 8082) - Authentication & JWT Management
5. **content-service** (Port 8083) - Content Management
6. **social-service** (Port 8084) - Social Features
7. **notification-service** (Port 8085) - Notifications
8. **admin-service** (Port 8086) - Administrative Features
9. **api-gateway** (Port 8080) - API Gateway & Routing

## Technologies Used
- **Spring Boot 3.1.5**
- **Spring Cloud 2022.0.4**
- **Spring Data JPA**
- **Spring Security**
- **Netflix Eureka** (Service Discovery)
- **Spring Cloud Config** (Configuration Management)
- **Spring Cloud Gateway** (API Gateway)
- **OpenFeign** (Inter-service Communication)
- **MySQL 8.0** (Database)
- **JWT** (Authentication)
- **Docker** (Containerization)

## Prerequisites
- **Java 17+**
- **Maven 3.6+**
- **MySQL 8.0+**
- **Docker** (optional)

## Database Setup
Create the following databases in MySQL:
```sql
CREATE DATABASE fondation_sociale_auth;
CREATE DATABASE fondation_sociale_user;
CREATE DATABASE fondation_sociale_content;
CREATE DATABASE fondation_sociale_social;
CREATE DATABASE fondation_sociale_notification;
CREATE DATABASE fondation_sociale_admin;
```

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/Assiaamahouch1/fondation-sociale-backend.git
cd fondation-sociale-backend
```

### 2. Build All Services
```bash
mvn clean install
```

### 3. Service Startup Order
Start services in the following order:

#### Step 1: Infrastructure Services
```bash
# 1. Config Service
cd config-service
mvn spring-boot:run

# 2. Discovery Service
cd ../discovery-service
mvn spring-boot:run
```

#### Step 2: Business Services
```bash
# 3. Auth Service
cd ../auth-service
mvn spring-boot:run

# 4. User Service
cd ../user-service
mvn spring-boot:run

# 5. Content Service
cd ../content-service
mvn spring-boot:run

# 6. Social Service
cd ../social-service
mvn spring-boot:run

# 7. Notification Service
cd ../notification-service
mvn spring-boot:run

# 8. Admin Service
cd ../admin-service
mvn spring-boot:run
```

#### Step 3: Gateway
```bash
# 9. API Gateway
cd ../api-gateway
mvn spring-boot:run
```

### 4. Using Docker (Alternative)
```bash
# Build all services
mvn clean package

# Build Docker images for each service
docker build -t fondation-config-service ./config-service
docker build -t fondation-discovery-service ./discovery-service
docker build -t fondation-auth-service ./auth-service
docker build -t fondation-user-service ./user-service
docker build -t fondation-content-service ./content-service
docker build -t fondation-social-service ./social-service
docker build -t fondation-notification-service ./notification-service
docker build -t fondation-admin-service ./admin-service
docker build -t fondation-api-gateway ./api-gateway

# Run with Docker Compose (create docker-compose.yml)
docker-compose up
```

## Service Details

### Auth Service (Port 8081)
**Complete Authentication & User Management**
- **Entities**: User (with UserRole enum: ADMIN, ADHERENT)
- **Features**: 
  - JWT-based authentication
  - User registration/login
  - Role-based access control
  - Password encryption
- **Endpoints**:
  - `POST /api/auth/login` - User login
  - `POST /api/auth/register` - User registration
  - `GET /api/auth/me` - Current user info
  - `POST /api/auth/validate` - Token validation
  - `GET /api/users` - Get all users
  - `GET /api/users/{id}` - Get user by ID
  - `PUT /api/users/{id}` - Update user
  - `DELETE /api/users/{id}` - Delete user

### Content Service (Port 8083)
**Content Management with Comments**
- **Entities**: Content, Comment
- **Enums**: ContentType (ARTICLE, EVENT, ANNOUNCEMENT, NEWS, DOCUMENT), ContentStatus (DRAFT, PUBLISHED, ARCHIVED, PENDING_REVIEW)
- **Features**:
  - Content CRUD operations
  - Comment system
  - Content categorization
  - View/Like tracking
- **Feign Integration**: UserFeignClient for user validation

### User Service (Port 8082)
**Extended User Management**
- Enhanced user profile management
- User preferences
- Profile pictures and additional information

### Social Service (Port 8084)
**Social Features**
- User following/followers
- Social interactions
- Activity feeds
- Community features

### Notification Service (Port 8085)
**Notification System**
- Email notifications
- In-app notifications
- Notification preferences
- Event-driven notifications

### Admin Service (Port 8086)
**Administrative Functions**
- System administration
- User management
- Content moderation
- Analytics and reporting

### API Gateway (Port 8080)
**Central Entry Point**
- Route management
- Load balancing
- CORS configuration
- Request/Response filtering

## Service Communication
Services communicate via:
1. **OpenFeign** - Synchronous HTTP calls
2. **Eureka Discovery** - Service registration and discovery
3. **Spring Cloud Config** - Centralized configuration

### Example Feign Client Usage
```java
@FeignClient(name = "auth-service", path = "/api/users")
public interface UserFeignClient {
    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable("id") Long id);
}
```

## Architecture Patterns Implemented

### 1. Abstract Service Pattern
```java
public abstract class AbstractService<E, GetDto, PostDto, ID> {
    public abstract List<GetDto> findAll();
    public abstract Optional<GetDto> findById(ID id);
    public abstract GetDto save(PostDto postDto);
    // ... other methods
}
```

### 2. Abstract Transformer Pattern
```java
public abstract class AbstractTransformer<E, GetDto, PostDto> {
    public abstract GetDto entityToGetDto(E entity);
    public abstract E postDtoToEntity(PostDto postDto);
    // ... other methods
}
```

### 3. DTO Pattern
- **GetDto**: For reading operations
- **PostDto**: For create/update operations
- Validation annotations
- Proper separation of concerns

## Configuration Management

### Config Server
- Centralized configuration
- Environment-specific properties
- Dynamic configuration updates
- Git-based configuration repository

### Application Properties
Each service has its own configuration:
- Database connections
- Service discovery settings
- Security configurations
- Business logic properties

## Security
- **JWT Authentication** in auth-service
- **Role-based access control** (ADMIN, ADHERENT)
- **Password encryption** using BCrypt
- **CORS configuration** in API Gateway
- **Security filters** and middleware

## Monitoring & Management
- **Spring Boot Actuator** endpoints
- **Health checks** for all services
- **Metrics collection**
- **Service status monitoring**

## API Documentation
Once running, access:
- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Individual Services**: http://localhost:{port}/actuator/health

## Common Endpoints
All services expose:
- `/actuator/health` - Health check
- `/actuator/info` - Service information
- `/actuator/metrics` - Service metrics

## Development Guidelines

### Code Organization
```
src/main/java/com/fondationsociale/{service}/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── enums/          # Enumerations
├── feign/          # Feign clients
├── repository/     # Data repositories
├── service/        # Service interfaces
│   └── impl/       # Service implementations
└── transformer/    # DTO transformers
```

### Best Practices
1. **Service Independence**: Each service has its own database
2. **Configuration Management**: Use Config Server for shared configuration
3. **Error Handling**: Proper exception handling and error responses
4. **Data Validation**: Use Bean Validation annotations
5. **Transaction Management**: Proper transaction boundaries
6. **Security**: Secure endpoints and validate permissions

## Troubleshooting

### Common Issues
1. **Service Registration**: Check Eureka dashboard for service status
2. **Database Connection**: Verify MySQL is running and databases exist
3. **Port Conflicts**: Ensure ports are available
4. **Configuration**: Check Config Server connectivity

### Logs
Check application logs for detailed error information:
```bash
tail -f logs/application.log
```

## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License
This project is licensed under the MIT License.

## Contact
For questions or support, please contact the development team.