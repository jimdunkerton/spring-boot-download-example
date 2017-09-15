package com.example.springbootdownloadexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class SpringBootDownloadExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDownloadExampleApplication.class, args);
	}
}
