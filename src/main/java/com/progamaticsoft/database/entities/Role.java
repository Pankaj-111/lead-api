package com.progamaticsoft.database.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.progamaticsoft.database.entities.enums.AppRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer roleId;

	@ToString.Exclude
	@Enumerated(EnumType.STRING)
	@Column(length = 32, name = "role_name")
	private AppRole roleName;

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

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JsonBackReference
	@ToString.Exclude
	private Set<User> users = new HashSet<>();

	public Role(AppRole roleName) {
		this.roleName = roleName;
	}
}
