package com.manta.Manta;

import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableJpaAuditing
@SpringBootApplication
public class MantaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MantaApplication.class, args);
	}

}
