package com.example.AccWeek1;

import com.example.AccWeek1.dtos.EmployeeDTO;
import com.example.AccWeek1.exceptions.EmployeeNotFound;
import com.example.AccWeek1.kafka.NotificationProducer;
import com.example.AccWeek1.mappers.EmployeeMapper;
import com.example.AccWeek1.models.Employee;
import com.example.AccWeek1.repositories.EmployeeRepo;
import com.example.AccWeek1.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//To test the SERVICE layer

//UPDATE: `testCreateEmployee` will always fail unless the Kafka processes are running - SAFE TO IGNORE
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepo repo;

    @Mock
    private NotificationProducer notifProd;

    @InjectMocks
    private EmployeeService service;

    @Test
    void testGetAll() {
        Employee e1 = new Employee(1L, "Lance", "Stroll", "Data Analyst", 28, "Bengaluru");
        Employee e2 = new Employee(2L, "Carlos", "Sainz", "Application Developer", 24, "Bengaluru");

        when(repo.findAll()).thenReturn(List.of(e1, e2));

        List<EmployeeDTO> result = service.getAllEmp();

        assertEquals(2, result.size());
        assertEquals("Carlos", result.get(1).firstName());
    }

    @Test
    void testGetById_Found() {
        Employee emp = new Employee(1L, "Lando", "Norris", "Manager", 30, "Mumbai");
        when(repo.findById(1L)).thenReturn(Optional.of(emp));

        EmployeeDTO dto = service.getEmpById(1L);
        assertEquals("Lando", dto.firstName());
    }

    @Test
    void testGetById_NotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFound.class, () -> service.getEmpById(1L));
    }

    @Test
    void testCreateEmployee() {
//        EmployeeDTO dto = new EmployeeDTO(1L, "Alex", "Albon", "Dev Tester", 25);
//        Employee saved = EmployeeMapper.toEntity(dto);
//        saved.setId(5L);
//
//        when(repo.save(any(Employee.class))).thenReturn(saved);
//
//        EmployeeDTO result = service.createEmp(dto);
//        assertEquals(5L, result.id());
//        assertEquals("Alex", result.firstName());
        EmployeeDTO dto = new EmployeeDTO(5L, "Alex", "Albon", "Dev Tester", 25, "Bengaluru");
        Employee savedEntity = EmployeeMapper.toEntity(dto);
        savedEntity.setId(5L);

        when(repo.save(any(Employee.class))).thenReturn(savedEntity);

        EmployeeDTO result = service.createEmp(dto);
        assertNotNull(result);
        assertEquals(5L, result.id());
        assertEquals("Alex", result.firstName());
        assertEquals("Albon", result.lastName());
        assertEquals("Dev Tester", result.role());
        assertEquals(25, result.age());
        assertEquals("Bengaluru", result.baseLocation());


        verify(repo, times(1)).save(any(Employee.class));
        // Ensure this verification is present:
        verify(notifProd, times(1)).sendNotif(eq("New Employee Created: Alex Albon"));
    }

    @Test
    void testDeleteEmployee() {
        service.deleteEmp(1L);
        verify(repo).deleteById(1L);
    }
}
