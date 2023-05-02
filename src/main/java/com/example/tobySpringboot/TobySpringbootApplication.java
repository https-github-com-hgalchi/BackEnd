package com.example.tobySpringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TobySpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TobySpringbootApplication.class, args);
	}


	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("expo://172.30.1.53:19000");
				registry.addMapping("/**").allowedOrigins("expo://172.30.1.53:19000")
						.allowCredentials(true);
			}
		};
	}

}
