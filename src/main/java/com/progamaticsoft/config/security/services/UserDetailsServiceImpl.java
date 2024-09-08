package com.progamaticsoft.config.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progamaticsoft.database.entities.EmailMaster;
import com.progamaticsoft.database.entities.MasterData;
import com.progamaticsoft.database.entities.MobileMaster;
import com.progamaticsoft.database.entities.User;
import com.progamaticsoft.database.repositories.EmailMasterRepository;
import com.progamaticsoft.database.repositories.MasterDataRepository;
import com.progamaticsoft.database.repositories.MobileMasterRepository;
import com.progamaticsoft.database.repositories.UserRepository;
import com.progamaticsoft.utils.Constants;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailMasterRepository emailMasterRepository;
	@Autowired
	private MobileMasterRepository moMasterRepository;
	@Autowired
	private MasterDataRepository masterDataRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userRepository.findByUserIdAndActiveAndDeleted(username, Constants.YES, Constants.NO)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		final EmailMaster email = emailMasterRepository
				.findByIdAndActiveAndDeleted(user.getEmailId(), Constants.YES, Constants.NO)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid user email not configured"));
		final MobileMaster mobile = moMasterRepository
				.findByIdAndActiveAndDeleted(user.getMobileId(), Constants.YES, Constants.NO)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid user mobile not configured"));

		final MasterData mobileIsd = masterDataRepository
				.findByIdAndActiveAndDeleted(mobile.getIsd(), Constants.YES, Constants.NO)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid user mobile ISD not configured"));

		return UserDetailsImpl.build(user, email.getEmail(), mobile.getMobile(), mobileIsd.getCode());
	}

}
