package com.example.AccWeek1.services;

import com.example.AccWeek1.dtos.EmployeeDTO;
import com.example.AccWeek1.dtos.EmployeeWithWeatherDTO;
import com.example.AccWeek1.exceptions.EmployeeNotFound;
import com.example.AccWeek1.kafka.NotificationProducer;
import com.example.AccWeek1.mappers.EmployeeMapper;
import com.example.AccWeek1.models.Employee;
import com.example.AccWeek1.repositories.EmployeeRepo;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

//This is the SERVICE (usable logic) layer
//This provides greater de-coupling between the controller and the actual logic the controller performs
//We can also use this SERVICE layer on multiple controllers!

//UPDATE - Since we have already established the DTO, we will update the service with DTO style logic!

//UPDATE 2.0 - All `get` methods are replaced to record notations

//UPDATE 3.0 - Setting up Micrometer for custom metrics
@Service
public class EmployeeService {
    private final EmployeeRepo repo;

    //Kafka Initialization
    @Autowired(required = false)
    private NotificationProducer notifProd;

    @Value("${kafka.enabled:false}")
    private boolean kafkaEnabled;


    //Micrometer Metrics SETUP:
    private final Counter employeeViewedCounter;

    @Autowired
    private WeatherService weatherService;

    public EmployeeService(EmployeeRepo repo, MeterRegistry meterRegistry) {
        this.repo = repo;

        //Micrometer
        this.employeeViewedCounter = Counter.builder("employee.viewed.total")
                                    .description("No. of Employee View Accesses: ")
                                    .register(meterRegistry);
    }
    public List<EmployeeDTO> getAllEmp() {
        employeeViewedCounter.increment();
        return repo.findAll().stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }

    public EmployeeWithWeatherDTO getEmployeeWithWeather(Long id) {
        employeeViewedCounter.increment();
        System.out.println("🔍 [Service] Fetching employee with ID: " + id);
        Optional<Employee> employeeOptional = repo.findById(id);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            System.out.println("✅ [Service] Employee found: " + employee.getBaseLocation());

            // Fetch weather data for employee's base location
            EmployeeWithWeatherDTO.WeatherInfo weatherInfo = weatherService.getWeatherByCity(employee.getBaseLocation());

            // Send notification if Kafka is enabled
//            if (kafkaEnabled && notifProd != null) {
//                try {
//                    String message = String.format("Weather data retrieved for employee %s %s in %s",
//                            employee.getFirstName(), employee.getLastName(), employee.getBaseLocation());
//                    notifProd.sendNotif(message);
//                } catch (Exception e) {
//                    System.err.println("⚠️ Kafka temporarily disabled: " + e.getMessage());
//                }
//            }

            return new EmployeeWithWeatherDTO(EmployeeMapper.toDto(employee), weatherInfo);

        }
        else {
            System.err.println("❌ [Service] No employee found with ID: " + id);
            return null;
        }

    }

    public EmployeeDTO getEmpById(Long id) {
        employeeViewedCounter.increment();
        Employee emp = repo.findById(id)
                .orElseThrow(() -> new EmployeeNotFound(id));
        return EmployeeMapper.toDto(emp);
    }

    public EmployeeDTO createEmp(EmployeeDTO dto) {
        Employee saved = repo.save(EmployeeMapper.toEntity(dto));
        EmployeeDTO savedDto = EmployeeMapper.toDto(saved);

//        notifProd.sendNotif("New Employee Created: " + savedDto.firstName()+ " " + savedDto.lastName() );

        return (EmployeeMapper.toDto(saved));
    }

    public EmployeeDTO updateEmp(Long id, EmployeeDTO dto) {
        Employee updated = repo.findById(id)
                .map(emp -> {
                    emp.setFirstName(dto.firstName());
                    emp.setLastName(dto.lastName());
                    emp.setRole(dto.role());
                    emp.setAge(dto.age());
                    emp.setBaseLocation(dto.baseLocation());
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
