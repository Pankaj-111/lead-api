package com.progamaticsoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progamaticsoft.aop.logging.Profile;
import com.progamaticsoft.utils.EncryptionUtil;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {
	@Autowired
	EncryptionUtil encryptionUtil;

	@Operation(summary = "Testing API")
//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200", description = "Successful operation", 
//			content = {
//					@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
//			@ApiResponse(responseCode = "404", description = "Example not found") })
	@Profile
	@GetMapping("/test")
	public String test(final HttpServletRequest request) {
		log.info("Encrypted value of {} : {}", "Pankaj", encryptionUtil.encrypt("Pankaj"));
		return Thread.currentThread().toString();
	}
}
