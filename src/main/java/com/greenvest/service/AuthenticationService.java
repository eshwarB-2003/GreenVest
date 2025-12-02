package com.greenvest.service;

import com.greenvest.common.PasswordHasher;
import com.greenvest.common.Preconditions;
import com.greenvest.model.Admin;
import com.greenvest.model.Buyer;
import com.greenvest.model.Seller;
import com.greenvest.model.User;
import com.greenvest.model.UserRole;
import com.greenvest.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public AuthenticationService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = Preconditions.requireNonNull(userRepository, "userRepository is required");
        this.passwordHasher = Preconditions.requireNonNull(passwordHasher, "passwordHasher is required");
    }

    public Optional<User> login(String username, String rawPassword) {
        Preconditions.requireNonBlank(username, "Username is required");
        Preconditions.requireNonBlank(rawPassword, "Password is required");

        Optional<User> userOpt = userRepository.findByUsername(username.trim());
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();
        boolean ok = passwordHasher.verifyPassword(rawPassword, user.getPasswordHash());
        return ok ? Optional.of(user) : Optional.empty();
    }

    public Seller registerSeller(String username, String rawPassword, String email, String organisationName) {
        Preconditions.requireNonBlank(username, "Username is required");
        Preconditions.requireNonBlank(rawPassword, "Password is required");
        Preconditions.requireNonBlank(organisationName, "Organisation name is required");

        ensureUsernameAvailable(username);

        String hash = passwordHasher.hashPassword(rawPassword);
        Seller seller = new Seller(username.trim(), hash, email, organisationName.trim());
        userRepository.save(seller);
        return seller;
    }

    public Buyer registerBuyer(String username, String rawPassword, String email, String companyName) {
        Preconditions.requireNonBlank(username, "Username is required");
        Preconditions.requireNonBlank(rawPassword, "Password is required");
        Preconditions.requireNonBlank(companyName, "Company name is required");

        ensureUsernameAvailable(username);

        String hash = passwordHasher.hashPassword(rawPassword);
        Buyer buyer = new Buyer(username.trim(), hash, email, companyName.trim());
        userRepository.save(buyer);
        return buyer;
    }

    private void ensureUsernameAvailable(String username) {
        userRepository.findByUsername(username.trim())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Username already taken");
                });
    }

    public void ensureDefaultAdmin() {
        List<User> all = userRepository.findAll();
        boolean hasAdmin = all.stream().anyMatch(u -> u.getRole() == UserRole.ADMIN);
        if (hasAdmin) {
            return;
        }

        String defaultUsername = "admin";
        String defaultPassword = "admin123";
        String hash = passwordHasher.hashPassword(defaultPassword);

        Admin admin = new Admin(defaultUsername, hash, "admin@greenvest.local");
        userRepository.save(admin);
    }
}
