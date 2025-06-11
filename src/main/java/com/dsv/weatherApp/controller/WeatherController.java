package com.dsv.weatherApp.controller;

import com.dsv.weatherApp.model.UserPreference;
import com.dsv.weatherApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {


    public final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    @GetMapping("/{city}")
    public String getWeather(@PathVariable String city){
        return weatherService.getWeather(city);
    }

    @PostMapping("/preference")
    public UserPreference savePreference(@RequestParam String userId, @RequestParam String city){
        return weatherService.savePreference(userId,city);
    }

    @GetMapping("/user/{userId}")
    public String getWeatherByUserId(@PathVariable String userId){
        return weatherService.getWeatherByUserId(userId);
    }
}
