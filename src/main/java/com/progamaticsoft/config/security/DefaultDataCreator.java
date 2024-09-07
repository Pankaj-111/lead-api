package com.progamaticsoft.config.security;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.progamaticsoft.database.entities.Role;
import com.progamaticsoft.database.entities.User;
import com.progamaticsoft.database.entities.enums.AppRole;
import com.progamaticsoft.database.repositories.RoleRepository;
import com.progamaticsoft.database.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DefaultDataCreator {

	@Bean
	CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		log.info("****** Creating default user in the system......");
		return args -> {
			final Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
					.orElseGet(() -> roleRepository.save(getRoleUser(AppRole.ROLE_USER)));

			final Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
					.orElseGet(() -> roleRepository.save(getRoleUser(AppRole.ROLE_ADMIN)));

			if (!userRepository.existsByUserId("user1")) {
				User user1 = getUser(passwordEncoder, userRole);
				userRepository.save(user1);
			}

			if (!userRepository.existsByUserId("admin")) {
				final User admin = getAdminUser(passwordEncoder, adminRole);
				userRepository.save(admin);
			}
			log.info("****** Default users created in the system......");
		};
	}

	private User getUser(PasswordEncoder passwordEncoder, Role userRole) {
		User user1 = new User();
		user1.setUserId("user1");
		user1.setPassword(passwordEncoder.encode("password1"));
		user1.setActive("Y");
		user1.setDeleted("N");
		user1.setFname("User");
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
		return user1;
	}

	private User getAdminUser(PasswordEncoder passwordEncoder, Role adminRole) {
		User admin = new User();
		admin.setUserId("admin");
		admin.setPassword(passwordEncoder.encode("password1"));
		admin.setActive("Y");
		admin.setDeleted("N");
		admin.setFname("Admin");
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
		admin.setRole(adminRole);
		return admin;
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
