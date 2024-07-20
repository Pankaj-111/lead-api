package com.progamaticsoft.config.enc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.progamaticsoft.utils.EncryptionUtil;

@Configuration
public class EncryptionConfiguration {
	@Autowired
	private EncryptionCofigBean configBean;

	@Bean
	EncryptionUtil createEncryptionBean() {
		System.out.println("configBean " + configBean);
		return new EncryptionUtil(configBean.getKey(), configBean.getInitVector());
	}

}
