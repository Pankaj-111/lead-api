package com.progamaticsoft.database.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.progamaticsoft.database.entities.EmailMaster;

public interface EmailMasterRepository extends CrudRepository<EmailMaster, Long> {
	Optional<EmailMaster> findByIdAndActiveAndDeleted(Long id, String active, String deleted);
}
