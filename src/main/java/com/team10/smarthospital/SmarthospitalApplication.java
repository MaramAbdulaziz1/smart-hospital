package com.team10.smarthospital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.team10.smarthospital.mapper")
public class SmarthospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmarthospitalApplication.class, args);
    }
}
