package com.admit.admit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.admit.admit.mapper")
@ComponentScan(basePackages = {"com.admit"})
public class AdmitApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdmitApplication.class, args);
	}

}
