package com.example.AccWeek1;

//Employee Mapper handles the communication between the actual database Entity - Employee.java
//And the Data Transfer Object - EmployeeDTO.java

public class EmployeeMapper {
    public static EmployeeDTO toDto(Employee emp) {
        return new EmployeeDTO(emp.getId(), emp.getFirstName(), emp.getLastName(), emp.getRole(), emp.getAge());
    }

    public static Employee toEntity(EmployeeDTO dto) {
        Employee emp = new Employee(dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getRole(), dto.getAge());

        if(dto.getId() != null) {
            emp.setId(dto.getId());
        }

        return emp;
    }

}
