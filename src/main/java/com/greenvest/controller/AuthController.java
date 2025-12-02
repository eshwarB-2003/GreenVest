package com.greenvest.controller;

import com.greenvest.model.Buyer;
import com.greenvest.model.Seller;
import com.greenvest.model.User;
import com.greenvest.service.AuthenticationService;

import java.util.Optional;

public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public Optional<User> handleLogin(String username, String password) {
        return authenticationService.login(username, password);
    }

    public Seller handleSellerRegistration(String username, String password, String email, String organisationName) {
        return authenticationService.registerSeller(username, password, email, organisationName);
    }

    public Buyer handleBuyerRegistration(String username, String password, String email, String companyName) {
        return authenticationService.registerBuyer(username, password, email, companyName);
    }

    public void ensureDefaultAdmin() {
        authenticationService.ensureDefaultAdmin();
    }
}
