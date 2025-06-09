package com.example.AccWeek1;


//This is the SpringMVC controller - The place where we define our HTTP routes

//UPDATE - We will be using DTOs to ensure that our API doesn't expose the database entity directly!

import jakarta.validation.Valid;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

//    private final EmployeeRepo repo;
//
//    EmployeeController(EmployeeRepo repo) {
//        this.repo = repo;
//    }

    //We will be using the SERVICE layer
    private final EmployeeService service;
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }


    //Landing Page
    @GetMapping("/")
    String home() {
        return "Welcome to Employee API";
    }

//    //Fetch all employees
//    @GetMapping("/employees")
//    List<Employee> getAll() {
//        return repo.findAll(); //These are methods inside the JpaRespository class - we are just using them!
//    }

//    //Fetching all employees - DTO Version
//    @GetMapping("/employees")
//    List<EmployeeDTO> getAll() {
//        return repo.findAll().stream()
//                .map(EmployeeMapper::toDto)
//                .toList();
//    }

    //Fetch all employees - SERVICE
    @GetMapping("/employees")
    List<EmployeeDTO> getAll() {
        return service.getAllEmp();
    }

//    //Fetch one employee
//    @GetMapping("/employees/{id}")
//    Employee getOne(@PathVariable Long id) {
//        //We have defined a custom exception, which will be triggered if we cannot find such an employee
//        return repo.findById(id)
//                .orElseThrow(() -> new EmployeeNotFound(id));
//    }

//    //Fetch one employee - DTO Version
//    @GetMapping("/employees/{id}")
//    EmployeeDTO getOne(@PathVariable Long id) {
//        Employee emp = repo.findById(id)
//                .orElseThrow(() -> new EmployeeNotFound(id));
//        return EmployeeMapper.toDto(emp);
//    }

    //Fetch one employee - SERVICE
    @GetMapping("/employees/{id}")
    EmployeeDTO getOne(@PathVariable Long id) {
        return service.getEmpById(id);
    }

//    //Post one employee
//    @PostMapping("/employees")
//    Employee newEmp(@Valid @RequestBody Employee newEmp) {
//        //We will pass a JSON through the "body" section of Postman
//        //The JSON will be saved as the new employee details!
//        return repo.save(newEmp);
//    }

//    //Post one employee - DTO Version
//    @PostMapping("/employees")
//    ResponseEntity<EmployeeDTO> newEmp(@Valid @RequestBody EmployeeDTO dto) {
//        Employee saved = repo.save(EmployeeMapper.toEntity(dto));
//        return ResponseEntity.ok(EmployeeMapper.toDto(saved));
//    }

    //Post one employee - SERVICE
    @PostMapping("/employees")
    ResponseEntity<EmployeeDTO> newEmp(@Valid @RequestBody EmployeeDTO dto) {
        EmployeeDTO saved = service.createEmp(dto);
        return ResponseEntity.ok(saved);
    }

//    //Edit one employee
//    //We get the new employee JSON and the ID of the target of the employee to edit (replace)
//    @PutMapping("employees/{id}")
//    Employee editEmp(@RequestBody Employee newEmp, @PathVariable Long id) {
//        return repo.findById(id)
//                .map(emp -> {
//                    emp.setFirstName(newEmp.getFirstName());
//                    emp.setLastName(newEmp.getLastName());
//                    emp.setRole(newEmp.getRole());
//                    emp.setAge(newEmp.getAge());
//
//                    //We use getters and setters to redefine the values of the existing employee entry
//                    return repo.save(emp);
//                })
//                //But if we cannot find an employee with that ID
//                //We must save the details in the JSON as a new employee
//                .orElseGet(() -> {
//                    return repo.save(newEmp);
//                });
//    }

//    //Edit one employee - DTO Version
//    @PutMapping("/employees/{id}")
//    ResponseEntity<EmployeeDTO> editEmp(@Valid @RequestBody EmployeeDTO dto, @PathVariable Long id) {
//        Employee updated = repo.findById(id)
//                        .map(emp -> {
//                            emp.setFirstName(dto.getFirstName());
//                            emp.setLastName(dto.getLastName());
//                            emp.setRole(dto.getRole());
//                            emp.setAge(dto.getAge());
//
//                            //We use getters and setters to redefine the values of the existing employee entry
//                            return repo.save(emp);
//                        })
//                .orElseGet(() -> {
//                    Employee newEmp = EmployeeMapper.toEntity(dto);
//                    newEmp.setId(id);
//                    return repo.save(newEmp);
//                });
//        return ResponseEntity.ok(EmployeeMapper.toDto(updated));
//    }

    //Edit one employee - SERVICE
    @PutMapping("/employees/{id}")
    ResponseEntity<EmployeeDTO> editEmp(@Valid @RequestBody EmployeeDTO dto, @PathVariable Long id) {
        EmployeeDTO updated = service.updateEmp(id, dto);
        return ResponseEntity.ok(updated);
    }

//    //Delete one employee
//    @DeleteMapping("/employees/{id}")
//    void deleteEmp(@PathVariable Long id) {
//        repo.deleteById(id);
//    }
//
    //Delete one employee - SERVICE
    @DeleteMapping("/employees/{id}")
    void deleteEmp(@PathVariable Long id) {
        service.deleteEmp(id);
    }
}
