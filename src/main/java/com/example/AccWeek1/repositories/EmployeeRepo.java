package com.example.AccWeek1.repositories;

import com.example.AccWeek1.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//We are using JPA's functionality
// <Domain, ID_type>
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
