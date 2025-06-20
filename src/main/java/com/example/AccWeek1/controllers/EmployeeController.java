package com.example.AccWeek1.controllers;


//This is the SpringMVC controller - The place where we define our HTTP routes

//UPDATE - We will be using DTOs to ensure that our API doesn't expose the database entity directly!

import com.example.AccWeek1.dtos.EmployeeDTO;
import com.example.AccWeek1.dtos.EmployeeWithWeatherDTO;
import com.example.AccWeek1.services.EmployeeService;
import com.example.AccWeek1.services.WeatherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    //We will be using the SERVICE layer
    @Autowired
    private final EmployeeService service;
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    //HTTP ROUTES:

    //Weather API Route:

    @Autowired
    private WeatherService weatherService;

    //Landing Page
    @GetMapping("/")
    String home() {
        return "Welcome to Employee API";
    }

    //Fetch all employees - SERVICE
    @GetMapping("/employees")
    List<EmployeeDTO> getAll() {
        return service.getAllEmp();
    }

    //Fetch one employee - SERVICE
    @GetMapping("/employees/{id}")
    EmployeeDTO getOne(@PathVariable Long id) {
        return service.getEmpById(id);
    }

    //Fetch one employee with WEATHER - SERVICE
    @GetMapping("/employees/{id}/location")
    public ResponseEntity<EmployeeWithWeatherDTO> getEmployeeWithWeather(@PathVariable Long id) {
        EmployeeWithWeatherDTO response = service.getEmployeeWithWeather(id);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //Post one employee - SERVICE
    @PostMapping("/employees")
    ResponseEntity<EmployeeDTO> newEmp(@Valid @RequestBody EmployeeDTO dto) {
        EmployeeDTO saved = service.createEmp(dto);
        return ResponseEntity.ok(saved);
    }

    //Edit one employee - SERVICE
    @PutMapping("/employees/{id}")
    ResponseEntity<EmployeeDTO> editEmp(@Valid @RequestBody EmployeeDTO dto, @PathVariable Long id) {
        EmployeeDTO updated = service.updateEmp(id, dto);
        return ResponseEntity.ok(updated);
    }

    //Delete one employee - SERVICE
    @DeleteMapping("/employees/{id}")
    void deleteEmp(@PathVariable Long id) {
        service.deleteEmp(id);
    }
}
