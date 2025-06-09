package com.example.AccWeek1;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

//Employee Model
@Entity
public class Employee {

    //ID
    @Id
    private Long id;

    //Name
    private String firstName;
    private String lastName;

    //Role
    private String role;

    //Age
    private int age;

    Employee() {}

    Employee(Long id, String firstName, String lastName, String role, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.age = age;
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
                Objects.equals(this.age, emp.age);
    }

    //Function to generate hash code of every entry
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.role, this.age);
    }

    //Function to print out the contents
    public String toString() {
        return
                "Employee: { id: " + id +
                        ", first_name: " + firstName +
                        ", last_name: " + lastName +
                        ", role: " + role +
                        ", age: " + age + " }";
    }
}
