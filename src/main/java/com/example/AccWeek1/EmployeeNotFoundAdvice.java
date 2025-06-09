package com.example.AccWeek1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeNotFoundAdvice {
    @ExceptionHandler(EmployeeNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String empNotFoundHandler(EmployeeNotFound ex) {
        return ex.getMessage();
    }
}
