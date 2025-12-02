package com.greenvest.model;

public class Seller extends User {

    private String organisationName;

    public Seller() {
        // for JSON
    }

    public Seller(String username, String passwordHash, String email, String organisationName) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.organisationName = organisationName;
        this.role = UserRole.SELLER;
    }

    public String getOrganisationName() {
        return organisationName;
    }
}
