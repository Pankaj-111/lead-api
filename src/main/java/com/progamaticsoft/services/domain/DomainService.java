package com.progamaticsoft.services.domain;

import com.progamaticsoft.dtos.request.domain.DomainRequest;
import com.progamaticsoft.dtos.response.domain.DomainResponse;

public interface DomainService {
	public DomainResponse createDomain(DomainRequest request);
}
