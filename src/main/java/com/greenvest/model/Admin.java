package com.greenvest.model;

public class Admin extends User {

    public Admin() {
        // for JSON
    }

    public Admin(String username, String passwordHash, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = UserRole.ADMIN;
    }
}
