package com.example.AccWeek1.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

//Employee Model

//UPDATE - Including 'Base Location' - To integrate OpenWeather data w/ Project
// Included validators for all fields as well

@Entity
public class Employee {

    //ID
    @Id
    private Long id;

    //Name
    @NotBlank(message = "Must mention a first name")
    private String firstName;

    @NotBlank(message = "Must mention a last name")
    private String lastName;

    //Role
    @NotBlank(message = "Must mention a role")
    private String role;

    //Age
    @NotNull(message = "Must mention an age")
    @Min(value = 18)
    @Max(value = 57)
    private int age;

    //Base Location
    @NotBlank(message = "Must mention a base location")
    private String baseLocation;

    Employee() {}

    public Employee(Long id, String firstName, String lastName, String role, int age, String baseLocation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.age = age;
        this.baseLocation = baseLocation;
    }

    //Getters:
    public Long getId() {
        return this.id;
    }

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

    public String getBaseLocation() { return this.baseLocation; }

    //--------------------------------------------------------------
    //Setters:
    public void setId(Long id) {
        this.id = id;
    }

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

    public void setBaseLocation(String baseLocation) { this.baseLocation = baseLocation; }

    //Function to check for duplicate entries
    public boolean equals(Object o) {
        if (this == o) return true;
        if ( !(o instanceof Employee) ) return false;

        Employee emp = (Employee) o;
        return
                Objects.equals(this.id, emp.id) &&
                Objects.equals(this.firstName, emp.firstName) &&
                Objects.equals(this.lastName, emp.lastName) &&
                Objects.equals(this.role, emp.role) &&
                Objects.equals(this.age, emp.age) &&
                Objects.equals(this.baseLocation, emp.baseLocation);
    }

    //Function to generate hash code of every entry
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.role, this.age, this.baseLocation);
    }

    //Function to print out the contents
    public String toString() {
        return
                "Employee: { id: " + id +
                        ", first_name: " + firstName +
                        ", last_name: " + lastName +
                        ", role: " + role +
                        ", age: " + age +
                        ", base_location: "+ baseLocation +
                            " }";
    }
}
