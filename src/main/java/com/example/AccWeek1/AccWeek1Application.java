package com.example.AccWeek1;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//UPDATE - Starting up the Dotenv dependency along with SpringBoot to avoid conflicts

@SpringBootApplication
public class AccWeek1Application {

	public static void main(String[] args) {

		// Load .env file before SpringApplication.run()
		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);
		//-----------------------------------------------------------

		SpringApplication.run(AccWeek1Application.class, args);
	}

}
