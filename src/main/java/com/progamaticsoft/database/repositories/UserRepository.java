package com.progamaticsoft.database.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progamaticsoft.database.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserIdAndActiveAndDeleted(String userId, String active, String deleted);

	Boolean existsByUserIdAndActiveAndDeleted(String userId, String active, String deleted);
}
