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
@Table(name = "email_master", indexes = { @Index(columnList = "active,deleted"),
		@Index(columnList = "createdate,modidate") }, uniqueConstraints = {
				@UniqueConstraint(name = "EMAIL_EMAIL", columnNames = { "email", "active", "deleted" }) })
public class EmailMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Email
	@Column(length = 128, name = "EMAIL", nullable = false)
	private String email;

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
