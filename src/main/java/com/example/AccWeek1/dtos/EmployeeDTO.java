package com.example.AccWeek1.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

//DTO - Data Transfer Object
//Used to transfer data between the client and the server!
//Without exposing our Database entity
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    //Role
    @NotBlank(message = "Role must be specified")
    private String role;

    //Age
    @Min(value = 18, message = "Age must be more than or at least 18")
    @Max(value = 57, message = "Age must be below 57")
    private int age;

    EmployeeDTO() {}

    public EmployeeDTO(Long id, String firstName, String lastName, String role, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.age = age;
    }

    //Getters:

    public Long getId() { return this.id; }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getRole() {
        return this.role;
    }

    public int getAge() {
        return this.age;
    }
    //--------------------------------------------------------------
    //Setters:

    public void setId(Long id) { this.id = id; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
