package com.example.AccWeek1.exceptions;

public class EmployeeNotFound extends RuntimeException {
    public EmployeeNotFound(Long id) {
        super("Oops! Could not find an employee with ID: " + id);
    }
}
