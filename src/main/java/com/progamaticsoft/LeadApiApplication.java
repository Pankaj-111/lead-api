package com.progamaticsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@EnableAspectJAutoProxy
@SpringBootApplication
public class LeadApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeadApiApplication.class, args);
	}

}
