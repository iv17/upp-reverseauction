package com.upp.reverseauction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upp.reverseauction.model.PrivateUser;

import java.util.Optional;

public interface PrivateUserRepository extends JpaRepository<PrivateUser, Long>{
	
	PrivateUser getById(long id);

	PrivateUser findByUsername(String username);

	Optional<PrivateUser> findByUsernameAndEmail(String username, String email);

	PrivateUser findByEmail(String email);

    PrivateUser findById(long userId);

    Optional<PrivateUser> findByUsernameAndPassword(String username, String password);
}

