package com.progamaticsoft.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ProjectSecurityConfig {
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				requests -> 
				requests.requestMatchers("/api/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				);
		http.csrf(AbstractHttpConfigurer::disable);
//        http.addFilterBefore(new CustomLoggingFilter(),
//                UsernamePasswordAuthenticationFilter.class);
		http.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}
