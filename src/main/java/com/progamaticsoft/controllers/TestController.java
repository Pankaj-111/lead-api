package com.progamaticsoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progamaticsoft.config.aop.logging.Profile;
import com.progamaticsoft.utils.EncryptionUtil;

@RestController
public class TestController {
	@Autowired
	EncryptionUtil encryptionUtil;

	@Profile
	@GetMapping("/test")
	public void test() {
		System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJ");
		System.out.println("GGGGGGGGGGGGGGGGGg" + encryptionUtil.encrypt("Pankaj"));
	}
}
