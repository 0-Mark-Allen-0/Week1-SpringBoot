package com.example.AccWeek1;

import com.example.AccWeek1.dtos.EmployeeDTO;
import com.example.AccWeek1.exceptions.EmployeeNotFound;
import com.example.AccWeek1.mappers.EmployeeMapper;
import com.example.AccWeek1.models.Employee;
import com.example.AccWeek1.repositories.EmployeeRepo;
import com.example.AccWeek1.services.EmployeeService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//To test the SERVICE layer
public class EmployeeServiceTest {
    private final EmployeeRepo repo = mock(EmployeeRepo.class);
    private final EmployeeService service = new EmployeeService(repo);

    @Test
    void testGetAll() {
        Employee e1 = new Employee(1L, "Lance", "Stroll", "Data Analyst", 28);
        Employee e2 = new Employee(2L, "Carlos", "Sainz", "Application Developer", 24);

        when(repo.findAll()).thenReturn(List.of(e1, e2));

        List<EmployeeDTO> result = service.getAllEmp();

        assertEquals(2, result.size());
        assertEquals("Carlos", result.get(1).getFirstName());
    }

    @Test
    void testGetById_Found() {
        Employee emp = new Employee(1L, "Lando", "Norris", "Manager", 30);
        when(repo.findById(1L)).thenReturn(Optional.of(emp));

        EmployeeDTO dto = service.getEmpById(1L);
        assertEquals("Lando", dto.getFirstName());
    }

    @Test
    void testGetById_NotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFound.class, () -> service.getEmpById(1L));
    }

    @Test
    void testCreateEmployee() {
        EmployeeDTO dto = new EmployeeDTO(null, "Alex", "Albon", "Dev Tester", 25);
        Employee saved = EmployeeMapper.toEntity(dto);
        saved.setId(5L);

        when(repo.save(any(Employee.class))).thenReturn(saved);

        EmployeeDTO result = service.createEmp(dto);
        assertEquals(5L, result.getId());
        assertEquals("Alex", result.getFirstName());
    }

    @Test
    void testDeleteEmployee() {
        service.deleteEmp(1L);
        verify(repo).deleteById(1L);
    }
}
