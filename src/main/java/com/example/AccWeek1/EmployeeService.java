package com.example.AccWeek1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//This is the SERVICE (usable logic) layer
//This provides greater de-coupling between the controller and the actual logic the controller performs
//We can also use this SERVICE layer on multiple controllers!

//UPDATE - Since we have already established the DTO, we will update the service with DTO style logic!
@Service
public class EmployeeService {
    private final EmployeeRepo repo;

    public EmployeeService(EmployeeRepo repo) {
        this.repo = repo;
    }

    //Kafka Setup:
    @Autowired
    private NotificationProducer notifProd;

    List<EmployeeDTO> getAllEmp() {
        return repo.findAll().stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }

    public EmployeeDTO getEmpById(Long id) {
        Employee emp = repo.findById(id)
                .orElseThrow(() -> new EmployeeNotFound(id));
        return EmployeeMapper.toDto(emp);
    }

    public EmployeeDTO createEmp(EmployeeDTO dto) {
        Employee saved = repo.save(EmployeeMapper.toEntity(dto));
        EmployeeDTO savedDto = EmployeeMapper.toDto(saved);

        notifProd.sendNotif("New Employee Created: " + savedDto.getFirstName() + " " + savedDto.getLastName() );

        return (EmployeeMapper.toDto(saved));
    }

    public EmployeeDTO updateEmp(Long id, EmployeeDTO dto) {
        Employee updated = repo.findById(id)
                .map(emp -> {
                    emp.setFirstName(dto.getFirstName());
                    emp.setLastName(dto.getLastName());
                    emp.setRole(dto.getRole());
                    emp.setAge(dto.getAge());
                    return repo.save(emp);
                })
                .orElseGet(() -> {
                    Employee newEmp = EmployeeMapper.toEntity(dto);
                    newEmp.setId(id);
                    return repo.save(newEmp);
                });

        return EmployeeMapper.toDto(updated);
    }

    void deleteEmp(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
