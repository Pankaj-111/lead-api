package com.progamaticsoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progamaticsoft.aop.logging.Profile;
import com.progamaticsoft.config.controlleradvice.ResourceNotFoundException;
import com.progamaticsoft.utils.EncryptionUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {
	@Autowired
	EncryptionUtil encryptionUtil;

	@Profile
	@GetMapping("/test")
	public String test(final HttpServletRequest request) {
		log.info("Encrypted value of {} : {}", "Pankaj", encryptionUtil.encrypt("Pankaj"));
		if (true) {
			throw new ResourceNotFoundException("ID  not found");
		}
		return Thread.currentThread().toString();
	}
}
