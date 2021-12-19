package com.example.proyectoEgg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class ProyectoEggApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoEggApplication.class, args);
	}

}
