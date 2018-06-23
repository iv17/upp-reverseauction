package com.upp.reverseauction.service;

import com.upp.reverseauction.model.Authorization;
import com.upp.reverseauction.model.PrivateUser;
import com.upp.reverseauction.repository.AuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final AuthorizationRepository authorizationRepository;

    @Autowired
    public AuthorizationService(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    public Authorization findByToken(String token) {
        return authorizationRepository.findByToken(token);
    }

    public Authorization createFor(PrivateUser user) {
        final Authorization authorization = new Authorization();
        authorization.setUser(user);

//        final AuthorizationDTO authorizationDTO = new AuthorizationDTO();
//        authorizationDTO.setToken(authorization.getToken());
//        authorizationDTO.setId(user.getId());
//        authorizationDTO.setRole(user.getRole());
//        authorizationDTO.setPermissions(user.getPermissions());
//        authorizationDTO.setVerified(user.isRegistrationConfirmed());
//
//        return authorizationDTO;
        return authorizationRepository.save(authorization);
    }

    public void deleteByToken(String token) {
        Authorization authorization = authorizationRepository.findByToken(token);
        authorizationRepository.delete(authorization);
    }

}
