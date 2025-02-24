package com.management.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductManagementServiceBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductManagementServiceBeApplication.class, args);
	}

}
