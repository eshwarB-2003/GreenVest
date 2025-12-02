package com.greenvest.model;

public class Buyer extends User {

    private String companyName;

    public Buyer() {
        // for JSON
    }

    public Buyer(String username, String passwordHash, String email, String companyName) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.companyName = companyName;
        this.role = UserRole.BUYER;
    }

    public String getCompanyName() {
        return companyName;
    }
}

