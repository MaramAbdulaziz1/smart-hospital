package com.team10.smarthospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.team10.smarthospital.model")
@EnableJpaRepositories("com.team10.smarthospital.repository")
public class SmarthospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmarthospitalApplication.class, args);
	}

}
