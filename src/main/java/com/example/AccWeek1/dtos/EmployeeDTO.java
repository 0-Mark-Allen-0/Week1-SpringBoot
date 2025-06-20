package com.example.AccWeek1.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//DTO - Data Transfer Object
//Used to transfer data between the client and the server!
//Without exposing our Database entity

//UPDATE - Replaced DTO boilerplate code with Java 16 Records instead

public record EmployeeDTO (
        @NotNull(message = "ID cannot be null")
        Long id,

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        //Role
        @NotBlank(message = "Role must be specified")
        String role,

        //Age
        @Min(value = 18, message = "Age must be more than or at least 18")
        @Max(value = 57, message = "Age must be below 57")
        @NotNull(message = "Age cannot be null")
        int age,

        @NotBlank(message = "Must mention a base location")
        String baseLocation


) { }
