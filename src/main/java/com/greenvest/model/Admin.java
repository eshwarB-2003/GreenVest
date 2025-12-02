package com.greenvest.model;

import com.greenvest.common.Preconditions;

public class Admin {

    private final String id;
    private final String username;
    private String passwordHash;

    public Admin(String id, String username, String passwordHash) {
        Preconditions.requireNonNull(id, "id must not be null");
        Preconditions.require(!username.isBlank(), "username must not be blank");
        Preconditions.require(!passwordHash.isBlank(), "passwordHash must not be blank");

        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void changePassword(String newPasswordHash) {
        Preconditions.require(!newPasswordHash.isBlank(), "passwordHash must not be blank");
        this.passwordHash = newPasswordHash;
    }
}
