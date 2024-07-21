package com.progamaticsoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progamaticsoft.aop.logging.Profile;
import com.progamaticsoft.utils.EncryptionUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class TestController {
	@Autowired
	EncryptionUtil encryptionUtil;

	@Profile
	@GetMapping("/test")
	public String test(final HttpServletRequest request) {
		System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJ");
		System.out.println("GGGGGGGGGGGGGGGGGg" + encryptionUtil.encrypt("Pankaj"));
		return Thread.currentThread().toString();
	}
}
