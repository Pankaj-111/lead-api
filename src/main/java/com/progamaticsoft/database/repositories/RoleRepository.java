package com.progamaticsoft.database.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.progamaticsoft.database.entities.Role;
import com.progamaticsoft.database.entities.enums.AppRole;

public interface RoleRepository extends CrudRepository<Role, Integer> {

	Optional<Role> findByRoleName(AppRole roleName);
}
