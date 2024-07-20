package com.progamaticsoft.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progamaticsoft.config.aop.logging.Profile;

@RestController
public class TestController {
	@Profile
	@GetMapping("/test")
	public void test() {
		System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJ");
		System.out.println("GGGGGGGGGGGGGGGGGg");
	}
}
