package com.manta.Manta.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manta.Manta.user.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	@Query("select userId from User where userId = ?1")
	Optional<User> findById(String userId);
	
}