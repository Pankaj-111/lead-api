package com.progamaticsoft.dtos.response.domain;

import com.progamaticsoft.dtos.request.domain.DomainRequest;

import lombok.Data;

@Data
public class DomainResponse {
	private boolean error = false;
	private String message;
	private DomainRequest domain;
}
