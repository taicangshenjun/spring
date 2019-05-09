package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableEurekaClient
public class MicroserviceProviderUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceProviderUserApplication.class, args);
	}

}
