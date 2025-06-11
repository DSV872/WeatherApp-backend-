# Weather Updates App

## Overview
The Weather Updates App is a backend application built with Java, Spring Boot, Hibernate, and Maven. It fetches current weather data for a specified city using the OpenWeatherMap API and allows users to save their preferred city in a MySQL database. The app exposes RESTful endpoints to retrieve weather data and manage user preferences.

### Features
- Fetch weather data for any city using the OpenWeatherMap API.
- Save and retrieve user preferences (preferred city) in a MySQL database.
- Logging of API requests and errors using Log4j.
- Error handling for API failures and invalid inputs.

## Tech Stack
- **Java**: 21.0.7
- **Spring Boot**: 3.x (as per dependencies in `pom.xml`)
- **Hibernate/JPA**: For database operations
- **Maven**: Build tool
- **MySQL**: Database for storing user preferences
- **OpenWeatherMap API**: For fetching weather data
- **Log4j**: For logging
- **RestTemplate**: For making HTTP requests to the OpenWeatherMap API

## Prerequisites
Before running the app, ensure you have the following installed:
- Java 21 (or compatible version)
- Maven
- MySQL (version 8.0 or higher)
- Postman (for testing API endpoints)
- An active internet connection (to fetch weather data)

## Setup Instructions

### 1. Clone the Repository
Clone this project to your local machine:
```bash
git clone <repository-url>
cd weatherApp
```

### 2. Configure MySQL Database
- Install MySQL if not already installed.
- Log in to MySQL:
  ```bash
  mysql -u root -p
  ```
- Create a database named `weather_db`:
  ```sql
  CREATE DATABASE weather_db;
  ```

### 3. Get an OpenWeatherMap API Key
- Sign up at [OpenWeatherMap](https://openweathermap.org/).
- Go to the "API Keys" section in your account dashboard.
- Generate a new API key (e.g., `your_new_api_key`).
- Note: It may take 10-15 minutes for the key to activate.

### 4. Configure `application.properties`
Update the `src/main/resources/application.properties` file with your MySQL credentials and OpenWeatherMap API key:
```
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/weather_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# OpenWeatherMap API Configuration
openweather.api.url=https://api.openweathermap.org/data/2.5/weather
openweather.api.key=your_new_api_key
```

### 5. Build and Run the App
- Build the project using Maven:
  ```bash
  mvn clean install
  ```
- Run the Spring Boot application:
  ```bash
  mvn spring-boot:run
  ```
- The app will start on `http://localhost:8080`.

### 6. Test the API Endpoints
Use Postman to test the following endpoints:

#### a. Get Weather for a City
- **Endpoint**: `GET /api/weather/{city}`
- **Example**: `http://localhost:8080/api/weather/London`
- **Response** (200 OK):
  ```json
  {
      "coord": {
          "lon": -0.1257,
          "lat": 51.5085
      },
      "main": {
          "temp": 15.5,
          "feels_like": 14.8,
          "humidity": 76
      },
      "name": "London",
      ...
  }
  ```

#### b. Save User Preference
- **Endpoint**: `POST /api/weather/preference?userId={userId}&city={city}`
- **Example**: `http://localhost:8080/api/weather/preference?userId=user1&city=London`
- **Response** (200 OK):
  ```json
  {
      "id": 1,
      "userId": "user1",
      "city": "London"
  }
  ```

#### c. Get Weather for a User
- **Endpoint**: `GET /api/weather/user/{userId}`
- **Example**: `http://localhost:8080/api/weather/user/user1`
- **Response** (200 OK): Same as the weather data for the user’s preferred city (e.g., London).

## Logging
- Logs are written to `logs/weather-app.log` using Log4j.
- Check this file for detailed request and error logs.

## Troubleshooting
- **401 Unauthorized Error**:
  - Ensure your OpenWeatherMap API key is valid and activated.
  - Wait 10-15 minutes after generating a new key.
  - Test the key directly: `https://api.openweathermap.org/data/2.5/weather?q=London&appid=your_new_api_key&units=metric`
- **MySQL Connection Issues**:
  - Verify MySQL is running and the credentials in `application.properties` are correct.
- **500 Internal Server Error**:
  - Check the logs for detailed error messages.
  - Ensure the app has an active internet connection to call the OpenWeatherMap API.

## Project Structure
- `WeatherAppApplication`: Main Spring Boot application class.
- `controller/WeatherController`: REST controller for handling API requests.
- `service/WeatherService`: Business logic for fetching weather data and managing preferences.
- `repository/UserPreferenceRepository`: JPA repository for database operations.
- `model/UserPreference`: Entity for storing user preferences.

## Future Improvements
- Add unit tests using JUnit and Mockito.
- Implement a frontend to interact with the backend.
- Add caching for weather data to reduce API calls.
- Support for more weather APIs as a fallback.

## License
This project is licensed under the MIT License.