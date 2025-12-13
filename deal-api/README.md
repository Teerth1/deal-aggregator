# Deal Aggregator API

[![CI/CD Pipeline](https://github.com/Teerth1/deal-aggregator/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/Teerth1/deal-aggregator/actions/workflows/ci-cd.yml)

A Spring Boot application that combines a Discord bot with options trading analysis, deal aggregation from Reddit, and Black-Scholes pricing calculations.

## Features

- **Discord Bot Integration** - 7 slash commands for market analysis
- **Black-Scholes Calculator** - Option pricing using the Black-Scholes model
- **Portfolio Management** - Track and manage option positions
- **Deal Scraper** - Automatically scrapes r/buildapcsales for deals
- **Live Market Data** - Real-time stock price integration
- **PostgreSQL Database** - Persistent storage for deals and holdings

## Discord Commands

- `/stock <ticker>` - View stock chart with live data
- `/optionprice` - Calculate theoretical option prices
- `/buy <contract> <price>` - Add option contract to portfolio
- `/sell <ticker>` - Remove position from portfolio
- `/portfolio` - View all active positions
- `/analyze <query>` - Fast analysis with live pricing
- `/price <product>` - Search for product deals

## Tech Stack

- **Backend**: Spring Boot 3.2.4
- **Database**: PostgreSQL 15
- **Discord**: JDA 5.0.0-beta.24
- **Web Scraping**: JSoup 1.17.2
- **Build Tool**: Maven
- **Java Version**: 17

## Getting Started

### Prerequisites

- Java 17 or higher
- PostgreSQL 15
- Maven 3.6+
- Discord Bot Token

### Environment Variables

Create a `.env` file or set the following environment variables:

```bash
DB_PASSWORD=your_postgres_password
DISCORD_BOT_TOKEN=your_bot_token
DISCORD_BOT_CHANNEL=your_channel_id
DISCORD_BOT_GUILD=your_guild_id
```

### Local Development

1. Clone the repository:
```bash
git clone https://github.com/Teerth1/deal-aggregator.git
cd deal-aggregator/deal-api
```

2. Start PostgreSQL:
```bash
docker run --name deal-postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=dealdb -p 5432:5432 -d postgres:15
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## CI/CD Pipeline

This project uses GitHub Actions for continuous integration and deployment:

### Pipeline Stages

1. **Build and Test**
   - Compiles the code with Maven
   - Runs unit and integration tests
   - Generates test coverage reports
   - Uploads test results as artifacts

2. **Code Quality Analysis**
   - Runs static code analysis
   - Verifies code style and best practices

3. **Security Scan**
   - Scans dependencies for known vulnerabilities
   - Generates security reports

4. **Docker Build** (on push to master)
   - Builds Docker image
   - Tags with commit SHA and latest
   - Pushes to Docker Hub (optional)

### Viewing Pipeline Results

- Go to the [Actions tab](https://github.com/Teerth1/deal-aggregator/actions) on GitHub
- Click on any workflow run to see detailed logs
- Download test reports and artifacts from completed runs

### Setting Up CI/CD

The pipeline runs automatically on:
- Push to `master` branch
- Pull requests to `master` branch

No additional setup required - just push your code!

## Database Schema

### Holdings Table
```sql
CREATE TABLE holdings (
    id SERIAL PRIMARY KEY,
    discord_user_id VARCHAR(255),
    ticker VARCHAR(10),
    type VARCHAR(10),
    strike_price DECIMAL,
    expiration DATE,
    buy_price DECIMAL
);
```

### Deals Table
```sql
CREATE TABLE deals (
    id SERIAL PRIMARY KEY,
    title VARCHAR(500),
    price VARCHAR(100),
    vendor VARCHAR(255),
    deal_url TEXT,
    category VARCHAR(100),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

## Project Structure

```
deal-api/
├── src/
│   ├── main/
│   │   ├── java/com/dealaggregator/dealapi/
│   │   │   ├── entity/          # JPA entities (Deal, Holding, User)
│   │   │   ├── repository/      # Spring Data JPA repositories
│   │   │   ├── service/         # Business logic services
│   │   │   └── config/          # Configuration classes
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── .github/
│   └── workflows/
│       └── ci-cd.yml           # CI/CD pipeline configuration
├── pom.xml
└── README.md
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

The CI/CD pipeline will automatically run tests on your PR!

## License

This project is licensed under the MIT License.

## Acknowledgments

- Discord JDA library for bot integration
- Black-Scholes model for option pricing
- r/buildapcsales community for deal data

---

**Built with [Claude Code](https://claude.com/claude-code)**
