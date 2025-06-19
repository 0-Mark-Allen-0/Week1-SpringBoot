package com.example.AccWeek1;

import com.example.AccWeek1.dtos.EmployeeDTO;
import com.example.AccWeek1.exceptions.EmployeeNotFound;
import com.example.AccWeek1.models.Employee;
import com.example.AccWeek1.repositories.EmployeeRepo;
import com.example.AccWeek1.services.EmployeeService;
import com.example.AccWeek1.services.WeatherService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock private EmployeeRepo repo;
    @Mock private MeterRegistry meterRegistry;
    @Mock private WeatherService weatherService;
    @Mock private Counter counter;

    private EmployeeService service;

    @BeforeEach
    void setUp() throws Exception {
        // Skip Micrometer builder entirely: use dummy registry and counter
        service = new EmployeeService(repo, meterRegistry);

        // Inject mocked counter directly (bypassing builder)
        Field counterField = EmployeeService.class.getDeclaredField("employeeViewedCounter");
        counterField.setAccessible(true);
        counterField.set(service, counter);

        // Inject WeatherService manually
        Field weatherField = EmployeeService.class.getDeclaredField("weatherService");
        weatherField.setAccessible(true);
        weatherField.set(service, weatherService);
    }

    @Test
    void testGetAll() {
        Employee e1 = new Employee(1L, "Lance", "Stroll", "Data Analyst", 28, "Bengaluru");
        Employee e2 = new Employee(2L, "Carlos", "Sainz", "App Dev", 24, "Pune");

        when(repo.findAll()).thenReturn(List.of(e1, e2));

        List<EmployeeDTO> result = service.getAllEmp();

        assertEquals(2, result.size());
        assertEquals("Carlos", result.get(1).firstName());
        verify(counter, times(1)).increment();
    }

    @Test
    void testGetById_Found() {
        Employee emp = new Employee(1L, "Lando", "Norris", "Manager", 30, "Mumbai");
        when(repo.findById(1L)).thenReturn(Optional.of(emp));

        EmployeeDTO dto = service.getEmpById(1L);
        assertEquals("Lando", dto.firstName());
        verify(counter, times(1)).increment();
    }

    @Test
    void testGetById_NotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFound.class, () -> service.getEmpById(1L));
        verify(counter, times(1)).increment();
    }

    @Test
    void testCreateEmployee() {
        EmployeeDTO dto = new EmployeeDTO(5L, "Alex", "Albon", "Dev Tester", 25, "Bengaluru");
        Employee savedEntity = new Employee(5L, "Alex", "Albon", "Dev Tester", 25, "Bengaluru");

        when(repo.save(any(Employee.class))).thenReturn(savedEntity);

        EmployeeDTO result = service.createEmp(dto);
        assertNotNull(result);
        assertEquals("Alex", result.firstName());
    }

    @Test
    void testDeleteEmployee() {
        service.deleteEmp(1L);
        verify(repo).deleteById(1L);
    }
}
