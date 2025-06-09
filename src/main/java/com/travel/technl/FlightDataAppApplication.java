package com.travel.technl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class FlightDataAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightDataAppApplication.class, args);
	}

}
