package com.torm.movierecommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MovierecommenderApplication {
	public static void main(String[] args) {
		SpringApplication.run(MovierecommenderApplication.class, args);
	}
}