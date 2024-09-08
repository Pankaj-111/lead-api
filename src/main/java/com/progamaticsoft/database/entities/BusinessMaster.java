package com.progamaticsoft.database.entities;

import java.io.Serializable;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "business_master", indexes = { @Index(columnList = "active,deleted"),
		@Index(columnList = "createdate,modidate") }, uniqueConstraints = {
				@UniqueConstraint(name = "BUSINESS_NAME_ACTIVE", columnNames = { "name", "active", "deleted" }) })
public class BusinessMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 64, name = "BNAME", nullable = false)
	private String name;
	@Column(length = 256, name = "DESCRIPTION")
	private String description;

	@Column(name = "EMAIL_ID", nullable = false)
	private Long emailId;

	@Column(name = "MOBILE_ID", nullable = false)
	private Long mobileId;

	@Column(name = "ISD", nullable = false)
	private Integer isd;

	@NotBlank
	@Size(max = 128)
	@Column(name = "CONTACT_PERSON", nullable = false)
	private String contactPerson;

	@Column(name = "PARENT_ID")
	private Integer parentId;

	@Column(name = "DOMAIN_ID", nullable = false)
	private Integer domainId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@CreationTimestamp
	private Date createdate;

	@Column(length = 1, name = "ACTIVE", nullable = false)
	private String active;

	@Column(length = 1, name = "DELETED", nullable = false)
	private String deleted;

	@UpdateTimestamp
	private Date modidate;

}