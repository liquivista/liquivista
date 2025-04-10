package com.management.product;

import com.management.product.config.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableSecurity
public class ProductManagementServiceBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductManagementServiceBeApplication.class, args);
	}

}
