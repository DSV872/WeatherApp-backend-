package com.dsv.weatherApp.service;

import com.dsv.weatherApp.model.UserPreference;
import com.dsv.weatherApp.repository.UserPreferenceRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WeatherService {

    private static final Logger logger = LogManager.getLogger(WeatherService.class);
    private final RestTemplate restTemplate;
    private final UserPreferenceRepository repository;

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.url}")
    private String apiUrl;

    public WeatherService(RestTemplate restTemplate, UserPreferenceRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public String getWeather(String city) {
        logger.info("Fetching weather for city: {}", city);
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            String url = apiUrl + "?q=" + encodedCity + "&appid=" + apiKey + "&units=metric";
            logger.debug("Calling OpenWeatherMap API with URL: {}", url);

            String response = restTemplate.getForObject(url, String.class);
            if (response == null) {
                logger.error("Received null response from OpenWeatherMap for city: {}", city);
                throw new RuntimeException("Failed to fetch weather data: No response from API");
            }
            logger.info("Weather data fetched successfully for city: {}", city);
            return response;
        } catch (RestClientException e) {
            logger.error("Error fetching weather data for city: {}. Error: {}", city, e.getMessage());
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage());
        }
    }

    public UserPreference savePreference(String userId, String city) {
        logger.info("Saving preference for user: {}, city: {}", userId, city);
        UserPreference preference = repository.findByUserId(userId)
                .orElse(new UserPreference());
        preference.setUserId(userId);
        preference.setCity(city);
        return repository.save(preference);
    }

    public String getWeatherByUserId(String userId) {
        logger.info("Fetching weather for user: {}", userId);
        UserPreference preference = repository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User Preference not found"));
        return getWeather(preference.getCity());
    }
}
//package com.dsv.weatherApp.service;
//
//import com.dsv.weatherApp.model.UserPreference;
//import com.dsv.weatherApp.repository.UserPreferenceRepository;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class WeatherService {
//
//    private static final Logger logger = LogManager.getLogger(WeatherService.class);
//    private final RestTemplate restTemplate;
//    private final UserPreferenceRepository repo;
//
//    @Value("${openweather.api.key")
//    private String apiKey;
//
//    @Value("${openweather.api.url}")
//    private String apiUrl;
//
//    public WeatherService(RestTemplate restTemplate, UserPreferenceRepository repo){
//        this.restTemplate = restTemplate;
//        this.repo=repo;
//    }
//    public String getWeather(String city) {
//        logger.info("Fetching weather for city: {}",city);
//        String url = apiUrl+"?q="+city+"&appid="+apiKey+"&units=metric";
//        String response = restTemplate.getForObject(url,String.class);
//        logger.info("Weather data fetched successfully for the city: {}",city);
//        return response;
//    }
//
//    public UserPreference savePreference(String userId, String city) {
//        logger.info("Saving preference for user: {}, city: {}",userId,city);
//        UserPreference preference = repo.findByUserId(userId)
//                .orElse(new UserPreference());
//        preference.setUserId(userId);
//        preference.setCity(city);
//        return repo.save(preference);
//    }
//
//    public String getWeatherByUserId(String userId) {
//        logger.info("Fetching weather for user: {}",userId);
//        UserPreference preference = repo.findByUserId(userId)
//                .orElseThrow(()->new RuntimeException("User Preference not found"));
//        return getWeather(preference.getCity());
//    }
//}
