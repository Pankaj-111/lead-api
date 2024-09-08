package com.progamaticsoft.database.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.progamaticsoft.database.entities.MasterData;

public interface MasterDataRepository extends CrudRepository<MasterData, Integer> {
	Optional<MasterData> findByIdAndActiveAndDeleted(Integer id, String active, String deleted);

	Optional<MasterData> findByCodeAndTypeAndActiveAndDeleted(String code, String type, String active, String deleted);

}
