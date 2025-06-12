package com.example.AccWeek1.services;

import com.example.AccWeek1.dtos.EmployeeDTO;
import com.example.AccWeek1.exceptions.EmployeeNotFound;
import com.example.AccWeek1.kafka.NotificationProducer;
import com.example.AccWeek1.mappers.EmployeeMapper;
import com.example.AccWeek1.models.Employee;
import com.example.AccWeek1.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//This is the SERVICE (usable logic) layer
//This provides greater de-coupling between the controller and the actual logic the controller performs
//We can also use this SERVICE layer on multiple controllers!

//UPDATE - Since we have already established the DTO, we will update the service with DTO style logic!

//UPDATE - All `get` methods are replaced to record notations
@Service
public class EmployeeService {
    private final EmployeeRepo repo;
    //Kafka Initialization
    private final NotificationProducer notifProd;

    public EmployeeService(EmployeeRepo repo, NotificationProducer notifProd) {
        this.repo = repo;
        this.notifProd = notifProd;
    }

    public List<EmployeeDTO> getAllEmp() {
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

        notifProd.sendNotif("New Employee Created: " + savedDto.firstName()+ " " + savedDto.lastName() );

        return (EmployeeMapper.toDto(saved));
    }

    public EmployeeDTO updateEmp(Long id, EmployeeDTO dto) {
        Employee updated = repo.findById(id)
                .map(emp -> {
                    emp.setFirstName(dto.firstName());
                    emp.setLastName(dto.lastName());
                    emp.setRole(dto.role());
                    emp.setAge(dto.age());
                    return repo.save(emp);
                })
                .orElseGet(() -> {
                    Employee newEmp = EmployeeMapper.toEntity(dto);
                    newEmp.setId(id);
                    return repo.save(newEmp);
                });

        return EmployeeMapper.toDto(updated);
    }

    public void deleteEmp(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
