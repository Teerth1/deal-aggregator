package com.dealaggregator.dealapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DealApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DealApiApplication.class, args);
	}

}
