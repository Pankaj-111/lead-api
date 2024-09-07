package com.progamaticsoft.config.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.progamaticsoft.config.security.jwt.AuthEntryPointJwt;
import com.progamaticsoft.config.security.jwt.AuthTokenFilter;
import com.progamaticsoft.database.entities.Role;
import com.progamaticsoft.database.entities.User;
import com.progamaticsoft.database.entities.enums.AppRole;
import com.progamaticsoft.database.repositories.RoleRepository;
import com.progamaticsoft.database.repositories.UserRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ProjectSecurityConfig {
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf ->
                csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/api/auth/public/**")
        );
       // http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((requests)
                -> requests
                .requestMatchers("/test").permitAll()
                .requestMatchers("/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/csrf-token").permitAll()
                .requestMatchers("/api/auth/public/**").permitAll()
                .anyRequest().authenticated());
        http.exceptionHandling(exception
                -> exception.authenticationEntryPoint(unauthorizedHandler));
        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		return args -> {
			Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
					.orElseGet(() -> roleRepository.save(getRoleUser(AppRole.ROLE_USER)));

			Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
					.orElseGet(() -> roleRepository.save(getRoleUser(AppRole.ROLE_ADMIN)));

			if (!userRepository.existsByUserId("user1")) {
				User user1 = new User();
				user1.setUserId("user1");
				user1.setPassword(passwordEncoder.encode("password1"));
				user1.setActive("Y");
				user1.setDeleted("N");
				user1.setFname("USER NAME1");
				user1.setBusinessId(0);
				user1.setCreatedBy(0L);
				user1.setAccountNonLocked(false);
				user1.setAccountNonExpired(true);
				user1.setCredentialsNonExpired(true);
				user1.setEnabled(true);
				user1.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
				user1.setAccountExpiryDate(LocalDate.now().plusYears(1));
				user1.setTwoFactorEnabled(false);
				user1.setSignUpMethod("email");
				user1.setRole(userRole);
				userRepository.save(user1);
			}

			if (!userRepository.existsByUserId("admin")) {
				User admin = new User();
				admin.setUserId("admin");
				admin.setPassword(passwordEncoder.encode("password1"));
				admin.setActive("Y");
				admin.setDeleted("N");
				admin.setFname("USER NAME1");
				admin.setBusinessId(0);
				admin.setCreatedBy(0L);
				admin.setAccountNonLocked(false);
				admin.setAccountNonExpired(true);
				admin.setCredentialsNonExpired(true);
				admin.setEnabled(true);
				admin.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
				admin.setAccountExpiryDate(LocalDate.now().plusYears(1));
				admin.setTwoFactorEnabled(false);
				admin.setSignUpMethod("email");
				admin.setRole(userRole);
				userRepository.save(admin);
			}
		};
	}

	private Role getRoleUser(AppRole approle) {
		Role userRole = new Role();
		userRole.setRoleName(approle);
		userRole.setActive("Y");
		userRole.setDeleted("N");
		userRole.setCreatedate(new Date());
		userRole.setModidate(new Date());
		userRole.setCreatedBy(0L);
		return userRole;
	}
}
