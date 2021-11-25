package com.SpringBoot.polyshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.SpringBoot.polyshop.config.StorageProperties;
import com.SpringBoot.polyshop.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class TechPolyshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechPolyshopApplication.class, args);
	}

//	khoi tao cac thu muc dung de luu tru
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args->{
			storageService.init();
		});
	}
}
