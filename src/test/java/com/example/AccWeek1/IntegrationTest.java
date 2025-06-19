package com.example.AccWeek1;

import com.example.AccWeek1.dtos.EmployeeDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = AccWeek1Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
@TestPropertySource(properties = {
        "spring.security.enabled=false", // Disabled security for this test
        "weather.api.key=MOCK_WEATHER_API_KEY",
        "openweather.api.url=https://api.openweathermap.org/data/2.5/weather"
})
public class IntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.36")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
        //Hibernate causes conflicts with MySQL
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQL8Dialect");
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/employees";
    }

    // ======================= TEST CASES ============================

    @Test
    @Order(1)
    void testCreateEmployee() {
        EmployeeDTO emp = new EmployeeDTO(99L, "Antonio", "Gio", "Regular Contractor", 29, "Indore");

        ResponseEntity<EmployeeDTO> response = restTemplate.postForEntity(getBaseUrl(), emp, EmployeeDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null after creation");
        assertEquals("Antonio", response.getBody().firstName(), "Created employee first name should match");
    }

    @Test
    @Order(2)
    void testGetEmployeeById() {
        ResponseEntity<EmployeeDTO> response = restTemplate.getForEntity(getBaseUrl() + "/99", EmployeeDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null when retrieving employee");
        assertEquals("Antonio", response.getBody().firstName(), "Retrieved employee first name should match");
        assertEquals("Indore", response.getBody().baseLocation(), "Retrieved employee base location should match");
    }

    @Test
    @Order(3)
    void testUpdateEmployee() {
        EmployeeDTO updatedEmp = new EmployeeDTO(99L, "Antonio", "Gio", "IT Consultant", 29, "Kochi");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmployeeDTO> entity = new HttpEntity<>(updatedEmp, headers);

        ResponseEntity<EmployeeDTO> response = restTemplate.exchange(getBaseUrl() + "/99", HttpMethod.PUT, entity, EmployeeDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null after update");
        assertEquals("IT Consultant", response.getBody().role(), "Employee role should be updated to IT Consultant");
        assertEquals("Kochi", response.getBody().baseLocation(), "Employee base location should be updated to Kochi");
    }

    @Test
    @Order(4)
    void testDeleteEmployee() {
        restTemplate.delete(getBaseUrl() + "/99");

        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl() + "/99", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Expected 404 NOT_FOUND after successful deletion");
    }
}
