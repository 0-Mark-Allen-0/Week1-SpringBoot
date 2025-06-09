package com.example.AccWeek1;

import org.springframework.data.jpa.repository.JpaRepository;

//We are using JPA's functionality
// <Domain, ID_type>
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
