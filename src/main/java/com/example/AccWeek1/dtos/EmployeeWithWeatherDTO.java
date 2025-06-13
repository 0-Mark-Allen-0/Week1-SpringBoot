package com.example.AccWeek1.dtos;

//We need a DTO to combine the data from the Employee and the OpenWeatherAPI

import com.example.AccWeek1.models.Employee;

public record EmployeeWithWeatherDTO (
        Employee employee,
        WeatherInfo weatherInfo
) {
    //Inner weather record

    public record WeatherInfo(
            String location,
            double currentTemperature,
            double minTemperature,
            double maxTemperature,
            String forecast,
            int humidity
    ) {}
}
