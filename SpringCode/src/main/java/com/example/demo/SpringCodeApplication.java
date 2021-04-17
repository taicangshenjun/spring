package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.demo.bean.Car;
import com.example.demo.config.MainConfig;

@SpringBootApplication
public class SpringCodeApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(SpringCodeApplication.class, args);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
		
		Car car = context.getBean("car", Car.class);
		System.out.println(car.getName());
	}

}
