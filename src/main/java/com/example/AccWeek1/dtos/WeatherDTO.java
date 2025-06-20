package com.example.AccWeek1.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

//DTO for OpenWeatherAPI
@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherDTO (
        @JsonProperty("main") MainWeatherInfo main,
        @JsonProperty("weather") WeatherDescription[] weather,
        @JsonProperty("name") String cityName
) {
    //Inner records for the nested JSON structure

    //MainWeatherInfo Record
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MainWeatherInfo(
            @JsonProperty("temp") double temperature,
            @JsonProperty("temp_min") double minTemperature,
            @JsonProperty("temp_max") double maxTemperature,
            @JsonProperty("humidity") int humidity
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record WeatherDescription(
            @JsonProperty("main") String main,
            @JsonProperty("description") String description
    ) {}
}
