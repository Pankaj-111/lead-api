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
import com.progamaticsoft.database.entities.EmailMaster;
import com.progamaticsoft.database.entities.MasterData;
import com.progamaticsoft.database.entities.MobileMaster;
import com.progamaticsoft.database.entities.Role;
import com.progamaticsoft.database.entities.User;
import com.progamaticsoft.database.entities.enums.AppRole;
import com.progamaticsoft.database.repositories.BusinessMasterRepository;
import com.progamaticsoft.database.repositories.DomainMasterRepository;
import com.progamaticsoft.database.repositories.EmailMasterRepository;
import com.progamaticsoft.database.repositories.MasterDataRepository;
import com.progamaticsoft.database.repositories.MobileMasterRepository;
import com.progamaticsoft.database.repositories.RoleRepository;
import com.progamaticsoft.database.repositories.UserRepository;
import com.progamaticsoft.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DefaultDataCreator {
	@Autowired
	private DomainMasterRepository domainMasterRepository;
	@Autowired
	private EmailMasterRepository emailMasterRepository;
	@Autowired
	private MobileMasterRepository moMasterRepository;
	@Autowired
	private MasterDataRepository masterDataRepository;

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

			final MasterData isd = masterDataRepository
					.findByCodeAndTypeAndActiveAndDeleted("+91", Constants.ISD_TYPE, Constants.YES, Constants.NO)
					.orElseGet(() -> masterDataRepository.save(getIsd("+91")));
			final MobileMaster mobile = moMasterRepository
					.findByMobileAndIsdAndActiveAndDeleted(9910062638L, isd.getId(), Constants.YES, Constants.NO)
					.orElseGet(() -> moMasterRepository.save(getMobile(isd.getId(), 9910062638L)));

			final EmailMaster busiEmail = emailMasterRepository
					.findByEmailAndActiveAndDeleted("business@gmail.com", Constants.YES, Constants.NO)
					.orElseGet(() -> emailMasterRepository.save(getEmail("business@gmail.com")));

			final BusinessMaster baseBusiness = businessRepository
					.findByNameAndActiveAndDeleted("Progmmatic Soft", Constants.YES, Constants.NO)
					.orElseGet(() -> businessRepository
							.save(creteBusiness("Progmmatic Soft", progamm, busiEmail, mobile)));
			log.info("Business created");
			User superAdmin = null;
			if (!userRepository.existsByUserIdAndActiveAndDeleted("superadmin", Constants.YES, Constants.NO)) {
				superAdmin = getUser(passwordEncoder, superAdminRole, "superadmin", "Super Admin", "12345",
						baseBusiness, null, busiEmail, mobile);
				userRepository.save(superAdmin);
			}
			log.info("Super admin user created");

			User admin = null;
			if (!userRepository.existsByUserIdAndActiveAndDeleted("admin", Constants.YES, Constants.NO)) {
				admin = getUser(passwordEncoder, adminRole, "admin", "Admin", "12345", baseBusiness, superAdmin,
						busiEmail, mobile);
				admin.setEmailId(busiEmail.getId());
				admin.setMobileId(mobile.getId());
				userRepository.save(admin);
			}
			log.info("Admin user created");
			User tl = null;
			if (!userRepository.existsByUserIdAndActiveAndDeleted("teamlead", Constants.YES, Constants.NO)) {
				tl = getUser(passwordEncoder, tlRole, "teamlead", "Team Lead", "12345", baseBusiness, admin, busiEmail,
						mobile);
				tl.setEmailId(busiEmail.getId());
				tl.setMobileId(mobile.getId());
				userRepository.save(tl);
			}
			log.info("Team lead user created");
			User user = null;
			if (!userRepository.existsByUserIdAndActiveAndDeleted("user", Constants.YES, Constants.NO)) {
				user = getUser(passwordEncoder, userRole, "user", "User", "12345", baseBusiness, tl, busiEmail, mobile);
				user.setEmailId(busiEmail.getId());
				user.setMobileId(mobile.getId());
				userRepository.save(user);
			}
			log.info("User created");
			log.info("****** Default users created in the system......");
		};
	}

	private MasterData getIsd(String code) {
		final MasterData isd = new MasterData();
		isd.setType(Constants.ISD_TYPE);
		isd.setCode(code);
		isd.setActive(Constants.YES);
		isd.setDeleted(Constants.NO);
		isd.setDescription("ISD code");
		isd.setCreatedBy(0L);
		return isd;
	}

	private MobileMaster getMobile(Integer isd, Long mobileNum) {
		final MobileMaster mobile = new MobileMaster();
		mobile.setMobile(mobileNum);
		mobile.setIsd(isd);
		mobile.setActive(Constants.YES);
		mobile.setDeleted(Constants.NO);
		mobile.setCreatedBy(0l);
		return mobile;
	}

	private EmailMaster getEmail(String emailId) {
		final EmailMaster email = new EmailMaster();
		email.setEmail(emailId);
		email.setActive(Constants.YES);
		email.setDeleted(Constants.NO);
		email.setCreatedBy(0l);
		return email;
	}

	private User getUser(PasswordEncoder passwordEncoder, Role userRole, String userId, String userName,
			String password, BusinessMaster progmmatic, User superUser, EmailMaster emailMaster,
			MobileMaster mobileMaster) {
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
		user.setEmailId(emailMaster.getId());
		user.setMobileId(mobileMaster.getId());
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

	private BusinessMaster creteBusiness(String name, DomainMaster domainMaster, EmailMaster emailMaster,
			MobileMaster mobileMaster) {
		BusinessMaster business = new BusinessMaster();
		business.setName(name);
		business.setContactPerson("Progammatic Soft");
		business.setEmailId(emailMaster.getId());
		business.setMobileId(mobileMaster.getId());
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
