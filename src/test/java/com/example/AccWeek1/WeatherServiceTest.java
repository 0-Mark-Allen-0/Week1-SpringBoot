package com.example.AccWeek1;

import com.example.AccWeek1.dtos.EmployeeWithWeatherDTO;
import com.example.AccWeek1.dtos.WeatherDTO;
import com.example.AccWeek1.services.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {


    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService service;

    @Test
    void testGetWeatherByCity_PASS() {

        WeatherDTO.MainWeatherInfo main = new WeatherDTO.MainWeatherInfo(27.5, 24.5, 28.0, 59);

        WeatherDTO.WeatherDescription[] weatherDesc = new WeatherDTO.WeatherDescription[] {
                new WeatherDTO.WeatherDescription("Clear", "clear sky")
        };

        WeatherDTO mockResponse = new WeatherDTO(main, weatherDesc, "Bengaluru");

        when (restTemplate.getForObject(anyString(), eq(WeatherDTO.class)))
                .thenReturn(mockResponse);

        //ACT:
        EmployeeWithWeatherDTO.WeatherInfo result = service.getWeatherByCity("Bengaluru");

        //ASSERT:
        assertNotNull(result);
        assertEquals("Bengaluru", result.location());
        assertEquals(27.5, result.currentTemperature());
        assertEquals("clear sky", result.forecast());
        assertEquals(59, result.humidity());
    }

    @Test
    void testGetWeatherByCity_FALLBACK() {
        lenient().when (restTemplate.getForEntity(anyString(), eq(WeatherDTO.class)))
                .thenThrow(new RuntimeException("FAILURE - Weather API"));

        EmployeeWithWeatherDTO.WeatherInfo result = service.getWeatherByCity("Chennai");

        assertNotNull(result);
        assertEquals("Chennai", result.location());
        assertEquals("Weather data unavailable", result.forecast());
        assertEquals(0.0, result.currentTemperature());
    }
}
