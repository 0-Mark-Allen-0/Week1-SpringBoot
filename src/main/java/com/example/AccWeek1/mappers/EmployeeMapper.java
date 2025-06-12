package com.example.AccWeek1.mappers;

//Employee Mapper handles the communication between the actual database Entity - Employee.java
//And the Data Transfer Object - EmployeeDTO.java

//UPDATE - All `get` methods are replaced to record notations

import com.example.AccWeek1.dtos.EmployeeDTO;
import com.example.AccWeek1.models.Employee;

public class EmployeeMapper {
    public static EmployeeDTO toDto(Employee emp) {
        return new EmployeeDTO(emp.getId(), emp.getFirstName(), emp.getLastName(), emp.getRole(), emp.getAge());
    }

    public static Employee toEntity(EmployeeDTO dto) {
        Employee emp = new Employee(dto.id(), dto.firstName(), dto.lastName(), dto.role(), dto.age());

        if(dto.id() != null) {
            emp.setId(dto.id());
        }

        return emp;
    }

}
