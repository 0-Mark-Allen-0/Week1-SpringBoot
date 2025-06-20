package com.example.AccWeek1.configurations;

import com.example.AccWeek1.models.Employee;
import com.example.AccWeek1.repositories.EmployeeRepo;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Pre-loading our database with some values, so that it is not empty!
@Configuration
public class LoadDatabase {

    //This is the class we invoke to log the activity in the console
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    //Creates a SpringBoot Bean to perform the CommandLineRunner when the application starts
    @Bean
    CommandLineRunner initDb(EmployeeRepo repo) {
        return args -> {
            log.info("Preloading " + repo.save(new Employee(1L, "Ayrton", "Senna", "Application Developer", 42, "Bengaluru")));
            log.info("Preloading " + repo.save(new Employee(2L, "Seb", "Vettel", "Tech Delivery", 28, "Chennai")));
            log.info("Preloading " + repo.save(new Employee(3L, "Nico", "Rosberg", "Ops Analyst", 37, "Pune")));
        };
    }
}
