package com.example.AccWeek1;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//UPDATE - Starting up the Dotenv dependency along with SpringBoot to avoid conflicts

@SpringBootApplication
public class AccWeek1Application {

	public static void main(String[] args) {

		// Load .env file before SpringApplication.run()
		Dotenv dotenv = Dotenv.configure()
				.directory(".")
				.filename(".env")
				.load();

		System.setProperty("weather.api.key", dotenv.get("WEATHER_API_KEY"));

		//-----------------------------------------------------------

		SpringApplication.run(AccWeek1Application.class, args);
	}

}
