# Frankfurter IDR Rate Aggregator

A production-ready Spring Boot REST API that aggregates Indonesian Rupiah (IDR) exchange rate data from the Frankfurter API, demonstrating advanced architectural patterns and clean code practices.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Personalization Details](#personalization-details)
- [Architectural Rationale](#architectural-rationale)

## 🎯 Overview

This application provides a single polymorphic REST API endpoint that serves three different types of financial data related to Indonesian Rupiah (IDR):

1. **Latest IDR Exchange Rates** - Current exchange rates with IDR as base currency, including a calculated USD buy spread
2. **Historical IDR to USD Rates** - Time series data for IDR to USD conversion (2024-01-01 to 2024-01-05)
3. **Supported Currencies** - Complete list of all currencies supported by the Frankfurter API

## ✨ Features

- **Strategy Pattern Implementation** - Polymorphic handling of multiple resource types without conditional logic
- **Custom FactoryBean** - Centralized RestClient configuration and lifecycle management
- **Startup Data Loading** - All data fetched once at application startup via ApplicationRunner
- **Thread-Safe In-Memory Store** - Immutable, concurrent-safe data storage
- **Unique Spread Calculation** - Personalized USD buy spread based on GitHub username
- **Comprehensive Error Handling** - Graceful handling of network failures and invalid requests
- **Production-Ready Testing** - Unit and integration tests with high coverage
- **Clean Architecture** - Clear separation of concerns across layers

## 🏗️ Architecture

### Technology Stack

- **Java 25**
- **Spring Boot 4.0.1**
- **Maven** - Build and dependency management
- **Lombok** - Boilerplate code reduction
- **JUnit 5 & Mockito** - Testing framework

### Project Structure

```
src/
├── main/
│   ├── java/com/fahri_allobank/Frankfurter/
│   │   ├── config/              # Configuration classes
│   │   │   ├── FrankfurterApiProperties.java
│   │   │   └── RestClientFactoryBean.java
│   │   ├── controller/          # REST controllers
│   │   │   └── FinanceController.java
│   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── LatestRatesResponse.java
│   │   │   ├── HistoricalRatesResponse.java
│   │   │   └── SupportedCurrenciesResponse.java
│   │   ├── runner/              # Startup runners
│   │   │   └── DataInitializationRunner.java
│   │   ├── services/            # Business logic
│   │   │   ├── FinanceDataService.java
│   │   │   └── FinanceDataStore.java
│   │   ├── strategy/            # Strategy pattern implementations
│   │   │   ├── IDRDataFetcher.java (interface)
│   │   │   └── impl/
│   │   │       ├── LatestIDRRatesFetcher.java
│   │   │       ├── HistoricalIDRUSDFetcher.java
│   │   │       └── SupportedCurrenciesFetcher.java
│   │   ├── util/                # Utility classes
│   │   │   └── SpreadCalculator.java
│   │   └── FrankfurterApplication.java
│   └── resources/
│       └── application.yml      # Application configuration
└── test/                        # Unit and integration tests
```

## 📦 Prerequisites

- **Java 25** or higher
- **Maven 3.6+**
- Internet connection (for fetching data from Frankfurter API)

## 🚀 Setup and Installation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Frankfurter
```

### 2. Build the Project

```bash
./mvnw clean install
```

This will:

- Download all dependencies
- Compile the source code
- Run all tests
- Package the application

## ▶️ Running the Application

### Using Maven

```bash
./mvnw spring-boot:run
```

### Using Java

```bash
java -jar target/Frankfurter-0.0.1-SNAPSHOT.jar
```

The application will start on **port 8080** by default.

### Startup Behavior

On startup, the application will:

1. Initialize the RestClient via FactoryBean
2. Execute the ApplicationRunner to fetch all data from Frankfurter API
3. Store the data in the thread-safe, immutable in-memory store
4. Mark the application as ready to serve requests

You should see log messages indicating successful data initialization:

```
INFO - Starting data initialization from Frankfurter API...
INFO - Fetching data for resource type: latest_idr_rates
INFO - Successfully stored data for resource type: latest_idr_rates
INFO - Fetching data for resource type: historical_idr_usd
INFO - Successfully stored data for resource type: historical_idr_usd
INFO - Fetching data for resource type: supported_currencies
INFO - Successfully stored data for resource type: supported_currencies
INFO - Data initialization completed successfully. Store is now immutable.
```

## 🔌 API Endpoints

### Base URL

```
http://localhost:8080/api/finance
```

### Endpoint

```
GET /api/finance/data/{resourceType}
```

### Supported Resource Types

#### 1. Latest IDR Rates

**Request:**

```bash
curl -X GET http://localhost:8080/api/finance/data/latest_idr_rates
```

**Response:**

```json
{
  "amount": 1.0,
  "base": "IDR",
  "date": "2024-01-09",
  "rates": {
    "USD": 0.000063,
    "EUR": 0.000058,
    "GBP": 0.00005,
    ...
  },
  "USD_BuySpread_IDR": 15873.015873
}
```

#### 2. Historical IDR to USD Rates

**Request:**

```bash
curl -X GET http://localhost:8080/api/finance/data/historical_idr_usd
```

**Response:**

```json
{
  "amount": 1.0,
  "base": "IDR",
  "start_date": "2024-01-01",
  "end_date": "2024-01-05",
  "rates": {
    "2024-01-01": {
      "USD": 0.000064
    },
    "2024-01-02": {
      "USD": 0.000063
    },
    ...
  }
}
```

#### 3. Supported Currencies

**Request:**

```bash
curl -X GET http://localhost:8080/api/finance/data/supported_currencies
```

**Response:**

```json
{
  "currencies": {
    "AUD": "Australian Dollar",
    "BGN": "Bulgarian Lev",
    "BRL": "Brazilian Real",
    "CAD": "Canadian Dollar",
    "CHF": "Swiss Franc",
    "CNY": "Chinese Renminbi Yuan",
    "CZK": "Czech Koruna",
    "DKK": "Danish Krone",
    "EUR": "Euro",
    "GBP": "British Pound",
    "HKD": "Hong Kong Dollar",
    "HUF": "Hungarian Forint",
    "IDR": "Indonesian Rupiah",
    "ILS": "Israeli New Sheqel",
    "INR": "Indian Rupee",
    "ISK": "Icelandic Króna",
    "JPY": "Japanese Yen",
    "KRW": "South Korean Won",
    "MXN": "Mexican Peso",
    "MYR": "Malaysian Ringgit",
    "NOK": "Norwegian Krone",
    "NZD": "New Zealand Dollar",
    "PHP": "Philippine Peso",
    "PLN": "Polish Złoty",
    "RON": "Romanian Leu",
    "SEK": "Swedish Krona",
    "SGD": "Singapore Dollar",
    "THB": "Thai Baht",
    "TRY": "Turkish Lira",
    "USD": "United States Dollar",
    "ZAR": "South African Rand"
  }
}
```

#### Error Response

**Request:**

```bash
curl -X GET http://localhost:8080/api/finance/data/invalid_resource
```

**Response (400 Bad Request):**

```json
{
  "error": "Invalid resource type: invalid_resource"
}
```

## 🧪 Testing

### Run All Tests

```bash
./mvnw test
```

### Run Specific Test Class

```bash
./mvnw test -Dtest=LatestIDRRatesFetcherTest
```

### Test Coverage

The project includes comprehensive test coverage with **18 total tests**:

- **Unit Tests** for all strategy implementations

  - `LatestIDRRatesFetcherTest` - 3 tests for latest rates fetching and spread calculation
  - `HistoricalIDRUSDFetcherTest` - 2 tests for historical data fetching
  - `SupportedCurrenciesFetcherTest` - 2 tests for currency list fetching
  - `SpreadCalculatorTest` - 3 tests for spread factor calculation logic

- **Integration Tests**
  - `FrankfurterApplicationTests` - 1 test for application context loading
  - `DataInitializationRunnerIntegrationTest` - 3 tests verifying startup data loading
  - `FinanceControllerIntegrationTest` - 4 tests for end-to-end API testing

**Latest Test Results:**

```
Tests run: 18, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
Total time: 4.105 s
```

## 🔐 Personalization Details

### GitHub Username

```
fahrimuda12
```

### Spread Factor Calculation

The spread factor is calculated using the following algorithm:

1. Convert GitHub username to lowercase: `"fahrimuda12"`
2. Calculate sum of Unicode values of all characters:
   - f(102) + a(97) + h(104) + r(114) + i(105) + m(109) + u(117) + d(100) + a(97) + 1(49) + 2(50) = 1044
3. Apply formula: `(1044 % 1000) / 100000.0 = 44 / 100000.0 = 0.00044`

### Calculated Spread Factor

```
0.00044
```

### USD Buy Spread Formula

```
USD_BuySpread_IDR = (1 / Rate_USD) * (1 + Spread Factor)
```

**Example Calculation:**

- If `Rate_USD = 0.000063` (from API when base=IDR)
- `USD_BuySpread_IDR = (1 / 0.000063) * (1 + 0.00044)`
- `USD_BuySpread_IDR = 15873.015873 * 1.00044`
- `USD_BuySpread_IDR ≈ 15880.00`

This represents the Rupiah selling rate to USD after applying the banking spread/margin.

---

## 🛠️ Architectural Rationale

### 1. Polymorphism Justification: Why Strategy Pattern?

**Question:** Why use the Strategy Pattern over a simpler conditional block in the service layer for handling the multi-resource endpoint?

**Answer:**

The Strategy Pattern was chosen over simple `if/else` or `switch` statements for several compelling reasons:

#### Extensibility

- **Open/Closed Principle**: Adding a new resource type requires only creating a new strategy class that implements `IDRDataFetcher`. No existing code needs to be modified.
- **Example**: If we need to add a "weekly_idr_summary" resource, we simply create `WeeklyIDRSummaryFetcher` without touching the controller, service, or other strategies.
- **Contrast**: With conditional logic, every new resource type requires modifying the service layer's switch/if-else block, increasing the risk of bugs and violating the Open/Closed Principle.

#### Maintainability

- **Single Responsibility**: Each strategy class has one clear responsibility - fetching and transforming data for its specific resource type.
- **Isolation**: Changes to one resource type's logic (e.g., modifying the spread calculation for latest rates) are isolated to that strategy class.
- **Testability**: Each strategy can be unit tested independently with mocked dependencies, making tests simpler and more focused.
- **Code Organization**: Related logic is grouped together in cohesive classes rather than scattered across a large conditional block.

#### Dependency Injection Benefits

- **Spring Integration**: Spring automatically discovers all `IDRDataFetcher` implementations and injects them as a `List<IDRDataFetcher>`, enabling automatic strategy registration.
- **Map-Based Lookup**: The service layer uses a map (`resourceType -> strategy`) for O(1) lookup, which is more efficient than sequential conditional checks.
- **No Manual Registration**: New strategies are automatically available without manual registration code.

#### Real-World Scalability

- In a production environment, different resource types might require different:
  - Caching strategies
  - Rate limiting rules
  - Data transformation logic
  - Error handling approaches
- The Strategy Pattern allows each implementation to handle these concerns independently.

### 2. Client Factory: Why FactoryBean?

**Question:** Explain the specific role and benefit of using a `FactoryBean` to construct the external API client. Why is this preferable to defining the client using a standard `@Bean` method?

**Answer:**

The `FactoryBean<RestClient>` approach provides several advantages over a simple `@Bean` method:

#### Lifecycle Control

- **Lazy Initialization**: FactoryBean allows for lazy initialization of the RestClient. The client is only created when first requested, not during application startup.
- **Custom Initialization Logic**: Complex initialization logic can be encapsulated within the factory, keeping configuration classes clean.
- **Singleton Control**: The `isSingleton()` method explicitly controls whether the factory produces a singleton or prototype bean.

#### Separation of Concerns

- **Configuration Abstraction**: The FactoryBean abstracts the complexity of RestClient creation from the rest of the application.
- **Centralized Configuration**: All client configuration (base URL, timeouts, headers, interceptors) is centralized in one place.
- **Type Safety**: The `getObjectType()` method provides explicit type information for dependency injection.

#### Flexibility and Extensibility

- **Easy Mocking**: In tests, the entire FactoryBean can be mocked or replaced with a test implementation.
- **Runtime Configuration**: The factory can make decisions about client configuration at runtime based on environment variables or profiles.
- **Multiple Clients**: If we need multiple RestClient instances with different configurations, we can create multiple FactoryBean implementations.

#### Production Benefits

- **Connection Pooling**: The factory can configure connection pooling, retry logic, and circuit breakers in one place.
- **Monitoring**: Interceptors for logging, metrics, and tracing can be added centrally.
- **Environment-Specific Configuration**: Different configurations for dev, staging, and production can be managed within the factory.

**Comparison with @Bean:**

```java
// Simple @Bean approach
@Bean
public RestClient restClient(FrankfurterApiProperties properties) {
    return RestClient.builder()
            .baseUrl(properties.getBaseUrl())
            .build();
}

// FactoryBean approach (current implementation)
@Component
public class RestClientFactoryBean implements FactoryBean<RestClient> {
    // Provides lifecycle control, type safety, and extensibility
}
```

The FactoryBean approach is more enterprise-ready and follows Spring's design philosophy of providing fine-grained control over bean creation.

### 3. Startup Runner Choice: Why ApplicationRunner?

**Question:** Justify the choice of using an `ApplicationRunner` over a simpler `@PostConstruct` method for initial data ingestion.

**Answer:**

`ApplicationRunner` was chosen over `@PostConstruct` for several critical reasons:

#### Application Lifecycle Timing

- **Full Context Initialization**: `ApplicationRunner` executes after the entire Spring application context is fully initialized and all beans are ready.
- **@PostConstruct Limitation**: `@PostConstruct` runs immediately after a bean's dependencies are injected, but before the full application context is ready.
- **Dependency Guarantee**: With ApplicationRunner, we're guaranteed that all strategies, the RestClient, and the data store are fully initialized and ready to use.

#### Error Handling and Application Startup

- **Startup Failure Control**: If data initialization fails in ApplicationRunner, the application startup fails gracefully with a clear error message.
- **@PostConstruct Limitation**: Exceptions in `@PostConstruct` can lead to partially initialized beans and unclear error states.
- **Production Safety**: In production, we want the application to fail fast if critical data cannot be loaded, rather than starting in a broken state.

#### Access to Application Arguments

- **Command-Line Arguments**: ApplicationRunner provides access to parsed application arguments via `ApplicationArguments`.
- **Future Extensibility**: This allows for future features like:
  - `--skip-data-init` flag for testing
  - `--data-source=mock` for development
  - Custom initialization parameters

#### Separation of Concerns

- **Clear Intent**: ApplicationRunner clearly signals "this is startup initialization logic" rather than "this is bean initialization logic".
- **Testability**: ApplicationRunner implementations are easier to test in isolation because they're separate components.
- **Multiple Runners**: Spring allows multiple ApplicationRunners with `@Order` annotations, enabling complex startup sequences.

#### Production Readiness

- **Logging and Monitoring**: ApplicationRunner execution is logged by Spring, making it easier to monitor startup times and failures.
- **Health Checks**: The application's readiness probe can check if ApplicationRunners have completed successfully.
- **Graceful Degradation**: In advanced scenarios, we could implement fallback logic if external API is unavailable at startup.

**Comparison:**

```java
// @PostConstruct approach (NOT recommended for this use case)
@PostConstruct
public void init() {
    // Runs during bean initialization
    // No guarantee other beans are ready
    // Limited error handling
    // No access to application arguments
}

// ApplicationRunner approach (current implementation)
@Component
public class DataInitializationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        // Runs after full context initialization
        // All beans guaranteed to be ready
        // Proper startup failure handling
        // Access to application arguments
    }
}
```

The ApplicationRunner approach aligns with Spring Boot's best practices for startup tasks and provides better control, error handling, and production readiness.

---

## 📄 License

This project is created as a take-home test for Allo Bank backend developer position.

## 👤 Author

**GitHub Username:** fahrimuda12

---

## 🙏 Acknowledgments

- [Frankfurter API](https://www.frankfurter.app/) - Free and open-source currency exchange rate API
- Spring Boot Team - For the excellent framework and documentation
