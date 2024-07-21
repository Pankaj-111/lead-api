package com.progamaticsoft.config.enc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.progamaticsoft.config.controlleradvice.exceptions.InvalidValueException;
import com.progamaticsoft.utils.EncryptionUtil;

@Configuration
public class EncryptionConfiguration {
	@Autowired
	private EncryptionCofigBean configBean;

	@Bean
	EncryptionUtil createEncryptionBean() {

		if (StringUtils.isBlank(configBean.getKey()) || configBean.getKey().length() < 16) {
			throw new InvalidValueException("Encryption key is mandatory and length must be 16 characters");
		}

		if (StringUtils.isBlank(configBean.getInitVector()) || configBean.getInitVector().length() < 16) {
			throw new InvalidValueException("Encryption Init Vector is mandatory and length must be 16 characters");
		}
		return new EncryptionUtil(configBean.getKey(), configBean.getInitVector());
	}

}
