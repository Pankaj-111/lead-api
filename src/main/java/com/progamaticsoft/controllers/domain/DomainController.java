package com.progamaticsoft.controllers.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progamaticsoft.config.controlleradvice.ErrorResponse;
import com.progamaticsoft.dtos.request.domain.DomainRequest;
import com.progamaticsoft.dtos.response.domain.DomainResponse;
import com.progamaticsoft.services.domain.DomainService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/domains")
public class DomainController {

	@Autowired
	private DomainService domainService;

	@Operation(summary = "This API will create a new domain, and take DomainRequest object as request body")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = DomainResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Resource not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized access exception", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	@PostMapping
	public DomainResponse crateDomain(@RequestBody @Valid final DomainRequest request) {
		return domainService.createDomain(request);
	}

	@Operation(summary = "This API will return all domains created by a user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = DomainResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Resource not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized access exception", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	@GetMapping
	public DomainResponse getDomains() {
		return domainService.getDomains();
	}
}
