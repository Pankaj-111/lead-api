package com.progamaticsoft.config.security;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.progamaticsoft.database.entities.BusinessMaster;
import com.progamaticsoft.database.entities.DomainMaster;
import com.progamaticsoft.database.entities.Role;
import com.progamaticsoft.database.entities.User;
import com.progamaticsoft.database.entities.enums.AppRole;
import com.progamaticsoft.database.repositories.BusinessMasterRepository;
import com.progamaticsoft.database.repositories.DomainMasterRepository;
import com.progamaticsoft.database.repositories.RoleRepository;
import com.progamaticsoft.database.repositories.UserRepository;
import com.progamaticsoft.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DefaultDataCreator {
	;

	@Autowired
	private DomainMasterRepository domainMasterRepository;

	@Bean
	CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder, BusinessMasterRepository businessRepository) {
		log.info("****** Creating default user in the system......");
		return args -> {

			// Creating super Admin role
			final Role superAdminRole = roleRepository.findByRoleName(AppRole.ROLE_SUPER)
					.orElseGet(() -> roleRepository.save(getRoleUser(AppRole.ROLE_SUPER)));
			log.info("Super admin role created");
			// Creating Admin role
			final Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
					.orElseGet(() -> roleRepository.save(getRoleUser(AppRole.ROLE_ADMIN)));
			log.info("Admin role created");
			// Creating Team lead role
			final Role tlRole = roleRepository.findByRoleName(AppRole.ROLE_TEAM_LEAD)
					.orElseGet(() -> roleRepository.save(getRoleUser(AppRole.ROLE_TEAM_LEAD)));
			log.info("Team lead role created");
			// Creating user role
			final Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
					.orElseGet(() -> roleRepository.save(getRoleUser(AppRole.ROLE_USER)));
			log.info("User role created");
			final DomainMaster progamm = domainMasterRepository
					.findByNameAndActiveAndDeleted("Lead Service", Constants.YES, Constants.NO)
					.orElseGet(() -> domainMasterRepository.save(creteDomain("Lead Service")));
			log.info("Domain created");
			final BusinessMaster baseBusiness = businessRepository
					.findByNameAndActiveAndDeleted("Progmmatic Soft", Constants.YES, Constants.NO)
					.orElseGet(() -> businessRepository.save(creteBusiness("Progmmatic Soft", progamm)));
			log.info("Business created");
			User superAdmin = null;
			if (!userRepository.existsByUserIdAndActiveAndDeleted("superadmin", Constants.YES, Constants.NO)) {
				superAdmin = getUser(passwordEncoder, superAdminRole, "superadmin", "Super Admin", "12345",
						baseBusiness, null);
				userRepository.save(superAdmin);
			}
			log.info("Super admin user created");

			User admin = null;
			if (!userRepository.existsByUserIdAndActiveAndDeleted("admin", Constants.YES, Constants.NO)) {
				admin = getUser(passwordEncoder, adminRole, "admin", "Admin", "12345", baseBusiness, superAdmin);
				userRepository.save(admin);
			}
			log.info("Admin user created");
			User tl = null;
			if (!userRepository.existsByUserIdAndActiveAndDeleted("teamlead", Constants.YES, Constants.NO)) {
				tl = getUser(passwordEncoder, tlRole, "teamlead", "Team Lead", "12345", baseBusiness, admin);
				userRepository.save(tl);
			}
			log.info("Team lead user created");
			User user = null;
			if (!userRepository.existsByUserIdAndActiveAndDeleted("user", Constants.YES, Constants.NO)) {
				user = getUser(passwordEncoder, userRole, "user", "User", "12345", baseBusiness, tl);
				userRepository.save(user);
			}
			log.info("User created");
			log.info("****** Default users created in the system......");
		};
	}

	private User getUser(PasswordEncoder passwordEncoder, Role userRole, String userId, String userName,
			String password, BusinessMaster progmmatic, User superUser) {
		final User user = new User();
		user.setUserId(userId);
		user.setPassword(passwordEncoder.encode(password));
		user.setActive(Constants.YES);
		user.setDeleted(Constants.NO);
		user.setFname(userName);
		user.setBusinessId(progmmatic.getId());
		Long createdBy = superUser != null ? superUser.getId() : 0l;
		user.setCreatedBy(createdBy);
		user.setAccountNonLocked(false);
		user.setAccountNonExpired(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		user.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
		user.setAccountExpiryDate(LocalDate.now().plusYears(1));
		user.setTwoFactorEnabled(false);
		user.setSignUpMethod("email");
		user.setRole(userRole);
		if (superUser != null) {
			user.setSuperUser(superUser.getId());
		}
		return user;
	}

	private DomainMaster creteDomain(String name) {
		DomainMaster domain = new DomainMaster();
		domain.setName(name);
		domain.setDeleted(Constants.NO);
		domain.setActive(Constants.YES);
		domain.setCreatedBy(0l);
		domain.setDescription("Progammatic Business");
		return domain;
	}

	private BusinessMaster creteBusiness(String name, DomainMaster domainMaster) {
		BusinessMaster business = new BusinessMaster();
		business.setName(name);
		business.setDeleted(Constants.NO);
		business.setActive(Constants.YES);
		business.setCreatedBy(0l);
		business.setDescription("Progammatic Business");
		business.setDomainId(domainMaster.getId());
		return business;
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
