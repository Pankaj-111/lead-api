package com.progamaticsoft.database.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(name = "USERS_ACTIVE", columnNames = { "userid", "active", "deleted" }),
		@UniqueConstraint(name = "USERS_DATE", columnNames = { "createdate", "modidate" }) })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@NotBlank
	@Size(max = 128)
	@Column(name = "USERID")
	private String userId;

	@NotBlank
	@Size(max = 128)
	@Column(name = "FNAME")
	private String fname;

	@Column(name = "BUSINESS_ID")
	private Integer businessId;

	@Size(max = 256)
	@Column(name = "password")
	@JsonIgnore
	private String password;

	@CreationTimestamp
	private Date createdate;

	@Column(length = 1, name = "ACTIVE", nullable = false)
	private String active = "Y";

	@Column(length = 1, name = "DELETED", nullable = false)
	private String deleted = "N";

	@UpdateTimestamp
	private Date modidate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	private boolean accountNonLocked = true;
	private boolean accountNonExpired = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;

	private LocalDate credentialsExpiryDate;
	private LocalDate accountExpiryDate;

	private String twoFactorSecret;
	private boolean isTwoFactorEnabled = false;
	private String signUpMethod;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "role_id", referencedColumnName = "role_id")
	@JsonBackReference
	@ToString.Exclude
	private Role role;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDate;

	@UpdateTimestamp
	private LocalDateTime updatedDate;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;
		return userId != null && userId.equals(((User) o).getUserId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}