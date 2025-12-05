# Deal Aggregator API

A robust RESTful API built with Spring Boot for aggregating and managing deals from various vendors. The API provides user authentication, deal management, and web scraping capabilities to collect deals from multiple sources.

## Features

- **User Authentication**: Secure user registration and login system
- **Deal Management**: Complete CRUD operations for deals
- **Deal Search**: Search deals by keyword or filter by category
- **Web Scraping**: Automated deal collection using JSoup
- **PostgreSQL Database**: Persistent data storage with JPA/Hibernate
- **RESTful API**: Clean and intuitive API endpoints

## Tech Stack

- **Java 17**
- **Spring Boot 4.0.0**
  - Spring Web MVC
  - Spring Data JPA
  - Spring Validation
- **PostgreSQL** - Database
- **Lombok** - Reduce boilerplate code
- **JSoup** - Web scraping library
- **Maven** - Build and dependency management

## Prerequisites

Before running this application, ensure you have:

- Java 17 or higher installed
- PostgreSQL database server running
- Maven 3.6+ installed
- Git (for cloning the repository)

## Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd deal-api
   ```

2. **Configure the database**

   Create a PostgreSQL database:
   ```sql
   CREATE DATABASE deal_aggregator;
   ```

3. **Update application properties**

   Edit `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/deal_aggregator
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The API will start on `http://localhost:8080`

## API Endpoints

### Authentication Endpoints

#### Register a new user
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "securepassword"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "johndoe",
  "password": "securepassword"
}
```

### Deal Endpoints

#### Get all deals
```http
GET /api/deals
```

#### Get deal by ID
```http
GET /api/deals/{id}
```

#### Create a new deal
```http
POST /api/deals
Content-Type: application/json

{
  "title": "50% Off Laptop",
  "price": 499.99,
  "originalPrice": 999.99,
  "discountPercentage": 50,
  "vendor": "TechStore",
  "dealUrl": "https://example.com/deal",
  "category": "Electronics",
  "dealType": "Limited Time",
  "description": "Amazing deal on high-performance laptop",
  "expiresAt": "2025-12-31T23:59:59"
}
```

#### Update a deal
```http
PUT /api/deals/{id}
Content-Type: application/json

{
  "title": "Updated Deal Title",
  "price": 449.99,
  ...
}
```

#### Delete a deal
```http
DELETE /api/deals/{id}
```

#### Get deals by category
```http
GET /api/deals/category/{category}
```

#### Search deals
```http
GET /api/deals/search?keyword=laptop
```

## Database Schema

### Users Table
| Column     | Type         | Constraints           |
|------------|--------------|----------------------|
| id         | BIGINT       | PRIMARY KEY, AUTO_INCREMENT |
| username   | VARCHAR      | NOT NULL, UNIQUE     |
| email      | VARCHAR      | NOT NULL, UNIQUE     |
| password   | VARCHAR      | NOT NULL             |
| role       | VARCHAR      | NOT NULL, DEFAULT 'USER' |
| created_at | TIMESTAMP    | NOT NULL             |
| updated_at | TIMESTAMP    |                      |

### Deals Table
| Column             | Type         | Constraints           |
|--------------------|--------------|----------------------|
| id                 | BIGINT       | PRIMARY KEY, AUTO_INCREMENT |
| title              | VARCHAR      | NOT NULL             |
| price              | DECIMAL      | NOT NULL             |
| original_price     | DECIMAL      |                      |
| discount_percentage| INTEGER      |                      |
| vendor             | VARCHAR      | NOT NULL             |
| deal_url           | VARCHAR      |                      |
| category           | VARCHAR      | NOT NULL             |
| deal_type          | VARCHAR      | NOT NULL             |
| description        | TEXT(1000)   |                      |
| created_at         | TIMESTAMP    | NOT NULL             |
| updated_at         | TIMESTAMP    |                      |
| expires_at         | TIMESTAMP    |                      |

## Project Structure

```
deal-api/
├── src/
│   ├── main/
│   │   ├── java/com/dealaggregator/dealapi/
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   └── DealController.java
│   │   │   ├── entity/
│   │   │   │   ├── User.java
│   │   │   │   └── Deal.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── DealRepository.java
│   │   │   ├── service/
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── DealService.java
│   │   │   │   └── DealScrapperService.java
│   │   │   └── DealApiApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/dealaggregator/dealapi/
│           └── DealApiApplicationTests.java
└── pom.xml
```

## Configuration

The application can be configured via `application.properties`:

```properties
# Application Name
spring.application.name=deal-api

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/deal_aggregator
spring.datasource.username=postgres
spring.datasource.password=password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server Configuration
server.port=8080
```

## Development

### Running Tests
```bash
mvn test
```

### Building for Production
```bash
mvn clean package
java -jar target/deal-api-0.0.1-SNAPSHOT.jar
```

## Future Enhancements

- JWT token-based authentication
- Email verification for user registration
- Deal rating and review system
- Automated scheduled web scraping
- Deal notifications and alerts
- Admin dashboard
- API rate limiting
- Swagger/OpenAPI documentation

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is open source and available under the MIT License.

## Contact

For questions or support, please open an issue in the GitHub repository.
