package com.example.AccWeek1.services;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//UPDATE - Including Resilience!
//Creating a Weather Service
@Service
public class WeatherService {

    //Using the Rest Template to call the API
    private final RestTemplate restTemplate = new RestTemplate();

    //API Key
    private final String apiKey;

    public WeatherService() {
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("WEATHER-API-KEY");
    }

    //Function to get the weather data
    @CircuitBreaker(name = "weatherCB", fallbackMethod = "weatherFB")
    public String getWeather(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        return restTemplate.getForObject(url, String.class);
    }

    String weatherFB(String city, Throwable throwable) {
        return "Weather service is currently unavailable!";
    }
}
