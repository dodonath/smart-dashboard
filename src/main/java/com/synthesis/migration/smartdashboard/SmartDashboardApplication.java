package com.synthesis.migration.smartdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class SmartDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartDashboardApplication.class, args);
	}
	
	
}
