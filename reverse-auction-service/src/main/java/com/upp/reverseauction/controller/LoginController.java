package com.upp.reverseauction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upp.reverseauction.controller.exception.NotFoundException;
import com.upp.reverseauction.dto.AuthorizationDTO;
import com.upp.reverseauction.dto.LoginCredentials;
import com.upp.reverseauction.model.Authorization;
import com.upp.reverseauction.model.PrivateUser;
import com.upp.reverseauction.service.AuthorizationService;
import com.upp.reverseauction.service.PrivateUserService;

@RestController
@RequestMapping("api/login")
@CrossOrigin(value = "*")
public class LoginController {

    private PrivateUserService privateUserService;

    private AuthorizationService authorizationService;

    @Autowired
    public LoginController(final PrivateUserService privateUserService,
                           final AuthorizationService authorizationService) {
        this.privateUserService = privateUserService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity hello() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity login(@RequestBody LoginCredentials loginCredentials) {
        PrivateUser user = privateUserService.findByUsernameAndPassword(loginCredentials.getUsername(),
                loginCredentials.getPassword()).orElseThrow(NotFoundException::new);
        Authorization authorization = authorizationService.createFor(user);

        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setId(authorization.getUser().getId());
        authorizationDTO.setToken(authorization.getToken());
        authorizationDTO.setPrivateUser(authorization.getUser().getCompany() == null);
        authorizationDTO.setUsername(authorization.getUser().getUsername());

        return new ResponseEntity<>(authorizationDTO, HttpStatus.OK);
    }

}
