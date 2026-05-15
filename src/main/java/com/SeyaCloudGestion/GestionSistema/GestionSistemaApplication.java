package com.SeyaCloudGestion.GestionSistema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GestionSistemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionSistemaApplication.class, args);
	}

}
