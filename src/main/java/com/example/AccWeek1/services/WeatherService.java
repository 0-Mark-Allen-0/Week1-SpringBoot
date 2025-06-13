package com.example.AccWeek1.services;

import com.example.AccWeek1.dtos.EmployeeWithWeatherDTO;
import com.example.AccWeek1.dtos.WeatherDTO;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

//UPDATE - Including Resilience!
//Creating a Weather Service

//UPDATE 2.0 - Needs a full overhaul since this will be integrated with a larger service

@Service
public class WeatherService {
    @Value("${openweather.api.key: your-api-key-here}")
    private String apiKey;

    @Value("${openweather.api.url:https://api.openweathermap.org/data/2.5/weather}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public WeatherService() {
        this.restTemplate = new RestTemplate();
    }

    @PostConstruct
    public void init() {
        System.out.println("✅ Loaded API Key: [" + apiKey + "]");
    }

    @CircuitBreaker(name = "weatherCB", fallbackMethod = "getDefaultWeatherInfo")
    public EmployeeWithWeatherDTO.WeatherInfo getWeatherByCity(String cityName) {
        try {
            String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, cityName, apiKey);
            System.out.println("Calling Weather API: " + url);
            WeatherDTO weatherData = restTemplate.getForObject(url, WeatherDTO.class);
            System.out.println("📦 Weather DTO raw: " + weatherData);

            if (weatherData != null && weatherData.main() != null) {
                return mapToWeatherInfo(weatherData);
            } else {
                return getDefaultWeatherInfo(cityName, new RuntimeException("No weather data received"));
            }

        }
        catch (RestClientException e) {
            System.err.println("Error fetching weather data for " + cityName + ": " + e.getMessage());
            return getDefaultWeatherInfo(cityName, e);
        }
    }

    private EmployeeWithWeatherDTO.WeatherInfo mapToWeatherInfo(WeatherDTO weatherData) {
        WeatherDTO.MainWeatherInfo main = weatherData.main();
        String forecast = "Clear";

        System.out.println("✅ Mapping weather data for city: " + weatherData.cityName());
        System.out.println("🌡️ Temp: " + main.temperature());
        System.out.println("📖 Forecast: " + (weatherData.weather() != null ? weatherData.weather()[0].description() : "null"));


        if (weatherData.weather() != null && weatherData.weather().length > 0) {
            forecast = weatherData.weather()[0].description();
        }

        return new EmployeeWithWeatherDTO.WeatherInfo(
                weatherData.cityName(),
                main.temperature(),
                main.minTemperature(),
                main.maxTemperature(),
                forecast,
                main.humidity()
        );
    }

    // Fallback method for circuit breaker
    public EmployeeWithWeatherDTO.WeatherInfo getDefaultWeatherInfo(String cityName, Exception ex) {
        System.err.println("Weather service fallback triggered for " + cityName + ": " + ex.getMessage());

        return new EmployeeWithWeatherDTO.WeatherInfo(
                cityName,
                0.0, // Default current temperature
                0.0, // Default min temperature
                0.0, // Default max temperature
                "Weather data unavailable", // Default forecast
                0 // Default humidity
        );
    }
}
