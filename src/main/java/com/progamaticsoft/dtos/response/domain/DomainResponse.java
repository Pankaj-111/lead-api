package com.progamaticsoft.dtos.response.domain;

import java.util.List;

import com.progamaticsoft.dtos.request.domain.DomainRequest;

import lombok.Data;

@Data
public class DomainResponse {
	private boolean error = false;
	private String message;
	private List<DomainRequest> domains;
}
