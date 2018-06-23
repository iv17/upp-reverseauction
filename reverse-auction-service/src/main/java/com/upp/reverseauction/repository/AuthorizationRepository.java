package com.upp.reverseauction.repository;


import com.upp.reverseauction.model.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

    Authorization findByToken(String token);
}
