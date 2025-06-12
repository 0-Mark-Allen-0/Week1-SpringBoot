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
        int age

) {
//    Can contain any custom methods or constructors
//    public EmployeeDTO(Long id, String firstName, String lastName, String role, int age) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.role = role;
//        this.age = age;
//    }
//
//    //Getters:
//
//    public Long getId() { return this.id; }
//
//    public String getFirstName() {
//        return this.firstName;
//    }
//
//    public String getLastName() {
//        return this.lastName;
//    }
//
//    public String getRole() {
//        return this.role;
//    }
//
//    public int getAge() {
//        return this.age;
//    }
//    //--------------------------------------------------------------
//    //Setters:
//
//    public void setId(Long id) { this.id = id; }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
}
