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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "master_data", indexes = { @Index(columnList = "type,active,deleted"),
		@Index(columnList = "createdate,modidate") })
public class MasterData implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 16, name = "CODE", nullable = false)
	private String code;

	private Integer parent;

	private Double sequence;

	@Column(length = 64, name = "TYPE", nullable = false)
	private String type;

	@Column(length = 256, name = "DESCRIPTION")
	private String description;

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