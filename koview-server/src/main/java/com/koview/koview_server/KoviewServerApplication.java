package com.koview.koview_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KoviewServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoviewServerApplication.class, args);
	}
}
