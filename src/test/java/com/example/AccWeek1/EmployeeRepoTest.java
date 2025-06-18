package com.example.AccWeek1;


import com.example.AccWeek1.models.Employee;
import com.example.AccWeek1.repositories.EmployeeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@DataJpaTest
@ContextConfiguration(initializers = EmployeeRepoTest.Initializer.class)
public class EmployeeRepoTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>
            ("mysql:8.0.36")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + mysql.getJdbcUrl(),
                    "spring.datasource.username=" + mysql.getUsername(),
                    "spring.datasource.password=" + mysql.getPassword(),
                    "spring.datasource.driver-class-name=com.mysql.cj.jdc.Driver"
            ).applyTo(context.getEnvironment());
        }
    }

    @Autowired
    private EmployeeRepo repo;

    @Test
    void testSaveAndFetch() {
        Employee emp = new Employee(23L, "Liam", "Lawson", "Junior Application Developer", 22, "Delhi");
        Employee saved = repo.save(emp);
        Employee found = repo.findById(saved.getId()).orElse(null);

        assertNotNull(found);
        assertEquals("Lawson", found.getLastName());
    }
}
