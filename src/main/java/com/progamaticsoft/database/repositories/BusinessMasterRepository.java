package com.progamaticsoft.database.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.progamaticsoft.database.entities.BusinessMaster;

public interface BusinessMasterRepository extends CrudRepository<BusinessMaster, Integer> {
	
	Optional<BusinessMaster> findByNameAndActiveAndDeleted(String name, String active, String deleted);
}
