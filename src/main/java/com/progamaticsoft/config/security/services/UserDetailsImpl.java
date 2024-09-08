package com.progamaticsoft.config.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.progamaticsoft.database.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String email;
	private Long mobile;
	private String isd;

	@JsonIgnore
	private String password;

	private boolean is2faEnabled;

	private Collection<? extends GrantedAuthority> authorities;

	public static UserDetailsImpl build(User user, String email, Long mobile, String isd) {
		final GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName().name());
		final UserDetailsImpl userDetails = new UserDetailsImpl();
		userDetails.setId(user.getId());
		userDetails.setUsername(user.getFname());
		userDetails.setAuthorities(List.of(authority));
		userDetails.set2faEnabled(user.isTwoFactorEnabled());
		userDetails.setPassword(user.getPassword());
		userDetails.setEmail(email);
		userDetails.setMobile(mobile);
		userDetails.setIsd(isd);
		return userDetails;

//       return new UserDetailsImpl(
//                user.getId(),
//                user.getUserId(),
//                user.getFname(),
//                user.getPassword(),
//                user.isTwoFactorEnabled(),
//                List.of(authority) // Wrapping the single authority in a list
//        );
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean is2faEnabled() {
		return is2faEnabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
