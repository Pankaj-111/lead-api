package com.progamaticsoft.database.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "mobile_master", indexes = { @Index(columnList = "active,deleted"),
		@Index(columnList = "createdate,modidate") }, uniqueConstraints = {
				@UniqueConstraint(name = "MOBILE_UUID", columnNames = { "mobileUUID", "active", "deleted" }),
				@UniqueConstraint(name = "MOBILE_ACTIVE", columnNames = { "mobile", "active", "deleted" }) })
public class MobileMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Email
	@Column(length = 10, name = "MOBILE", nullable = false)
	private Long mobile;

	@Column(name = "MOBILE_ISD", nullable = false)
	private Integer isd;

	@Column(length = 512, name = "EMAIL_UUID")
	private String mobileUUID;

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
}
