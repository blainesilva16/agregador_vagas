package com.devnotfound.talenthub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TalenthubApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalenthubApplication.class, args);
	}

}
