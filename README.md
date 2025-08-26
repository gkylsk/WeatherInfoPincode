# Weather API
Weather Information Based on Pincode and Date.

This project integrates with the [OpenWeatherAPI](https://openweathermap.org/current) to fetch weather information

## Features
- Fetch weather data for a given pincode and date
- Store weather information by pincode and date in a PostgreSQL database
- Swagger UI for interactive API documentation

## Technologies Used
- Spring Boot: Framework to build production-ready Java applications quickly with minimal configuration
- Spring Data JPA: Simplifies database interaction using Java Persistence API and repository abstractions
- PostgreSQL: Open-source relational database for efficient and reliable data storage
- JUnit: Testing framework for writing and running unit tests in Java
- RestTemplate: Synchronous client used to perform HTTP requests to interact with RESTful web services
- Swagger: Generate interactive API documentation

## Setup & Installation

### Clone the Repository
  ```bash
git clone https://github.com/gkylsk/WeatherInfoPincode.git
```
### Change the working directory
```bash
cd Weather-Info-Pincode
```
### Add API Key
- Sign in/ Create Account at [OpenWeather](https://openweathermap.org/current)
- Navigate to User -> My API Keys
- Generate a new key
- Copy the generated key into application.properties
```properties
api.key=YOUR_API_KEY_HERE
```

### Running the Application
```bash
mvn spring-boot:run
```
### Port
The application runs on **port 8080** by default.  
Base URL: `http://localhost:8080`

### API Documentation (Swagger UI)
After starting the application, open:
http://localhost:8080/swagger-ui/index.html

### Running the Tests
```bash
mvn test
```

## API Endpoints
### Request
GET  /api/weather?pincode={pincode}&for_date={yyyy-mm-dd} 

### Parameters
- pincode : Integer(required)
- for_date : String(required, format: yyyy-mm-dd)
