package com.mapic.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.mapic.backend.entity")
@EnableJpaRepositories(basePackages = "com.mapic.backend.repository")
public class MapicBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapicBackendApplication.class, args);
	}

}
