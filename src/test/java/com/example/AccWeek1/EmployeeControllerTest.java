package com.example.AccWeek1;

import com.example.AccWeek1.configurations.SecurityConfig;
import com.example.AccWeek1.controllers.EmployeeController;
import com.example.AccWeek1.dtos.EmployeeDTO;
import com.example.AccWeek1.services.EmployeeService;
import com.example.AccWeek1.services.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// UPDATE: Removed @TestConfiguration
// Had to include @MockitoBean for the WeatherService as well - was causing the failures
// Had to remove `null` from the input for testCreateEmployee since @GeneratedValue was removed
// @TestConfiguration has been removed entirely and the test work without it!

@WebMvcTest(controllers = EmployeeController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmployeeService service;

    @MockitoBean
    private WeatherService weatherService;

    @Test
    void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to Employee API"));
    }

    @Test
    void testGetAll() throws Exception {
        // Given
        EmployeeDTO e1 = new EmployeeDTO(1L, "Lance", "Stroll", "Data Analyst", 28, "Bengaluru");
        EmployeeDTO e2 = new EmployeeDTO(2L, "Carlos", "Sainz", "Application Developer", 24, "Bengaluru");

        when(service.getAllEmp()).thenReturn(List.of(e1, e2));

        // When & Then
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Lance"))
                .andExpect(jsonPath("$[1].firstName").value("Carlos"));
    }

    @Test
    void testCreateEmployee() throws Exception {
        // Given
        EmployeeDTO input = new EmployeeDTO(1L, "Alex", "Albon", "QA", 25, "Bengaluru");
        EmployeeDTO output = new EmployeeDTO(1L, "Alex", "Albon", "QA", 25, "Bengaluru");

        when(service.createEmp(any(EmployeeDTO.class))).thenReturn(output);

        // When & Then
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Alex"));
    }

    @Test
    void testEditEmployee() throws Exception {
        // Given
        EmployeeDTO updated = new EmployeeDTO(1L, "Charles", "Leclerc", "Tester", 26, "Hyderabad");

        when(service.updateEmp(eq(1L), any(EmployeeDTO.class))).thenReturn(updated);

        // When & Then
        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Charles"))
                .andExpect(jsonPath("$.age").value(26));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        // Given
        doNothing().when(service).deleteEmp(1L);

        // When & Then
        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isOk());
    }
}