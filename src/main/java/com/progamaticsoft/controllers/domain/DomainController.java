package com.progamaticsoft.controllers.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progamaticsoft.dtos.request.domain.DomainRequest;
import com.progamaticsoft.dtos.response.domain.DomainResponse;
import com.progamaticsoft.services.domain.DomainService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/domain")
public class DomainController {
	
	@Autowired
	private DomainService domainService;

	@PostMapping("/create")
	public DomainResponse crateDomain(@RequestBody @Valid final DomainRequest request) {
		return domainService.createDomain(request);
	}
}
