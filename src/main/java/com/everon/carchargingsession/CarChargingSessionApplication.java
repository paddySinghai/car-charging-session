package com.everon.carchargingsession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class CarChargingSessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarChargingSessionApplication.class, args);
	}

}
