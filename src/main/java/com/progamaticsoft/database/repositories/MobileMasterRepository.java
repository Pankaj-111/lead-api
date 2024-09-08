package com.progamaticsoft.database.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.progamaticsoft.database.entities.EmailMaster;
import com.progamaticsoft.database.entities.MobileMaster;

public interface MobileMasterRepository extends CrudRepository<MobileMaster, Long> {
	Optional<MobileMaster> findByIdAndActiveAndDeleted(Long id, String active, String deleted);
}
