package com.progamaticsoft.config.enc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "encryption.util")
public class EncryptionCofigBean {
private String key;
private String initVector;
}
