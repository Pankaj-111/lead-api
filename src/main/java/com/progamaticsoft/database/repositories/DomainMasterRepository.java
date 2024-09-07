package com.progamaticsoft.database.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.progamaticsoft.database.entities.DomainMaster;

public interface DomainMasterRepository extends CrudRepository<DomainMaster, Integer> {
	Optional<DomainMaster> findByNameAndActiveAndDeleted(String name, String active, String deleted);
}
