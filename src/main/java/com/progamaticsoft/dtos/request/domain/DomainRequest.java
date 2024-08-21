package com.progamaticsoft.dtos.request.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DomainRequest {
	private Integer id;
	@NotBlank(message = "Domain Name is mandatory")
	private String name;
	private String description;
	private Integer parentId;
	private Double sequence;
}
