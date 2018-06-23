package com.upp.reverseauction.service;

import com.upp.reverseauction.model.PrivateUser;
import com.upp.reverseauction.repository.PrivateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrivateUserService {

    private PrivateUserRepository privateUserRepository;

    @Autowired
    public PrivateUserService(final PrivateUserRepository privateUserRepository) {
        this.privateUserRepository = privateUserRepository;
    }

    public PrivateUser save(PrivateUser user) {
        return privateUserRepository.save(user);
    }

    public PrivateUser findByUsername(String username) {
        return this.privateUserRepository.findByUsername(username);
    }

    public Optional<PrivateUser> findByUsernameAndPassword(String username, String password) {
        return this.privateUserRepository.findByUsernameAndPassword(username, password);
    }
}
