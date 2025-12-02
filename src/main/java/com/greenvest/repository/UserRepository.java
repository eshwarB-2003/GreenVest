package com.greenvest.repository;

import com.greenvest.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(String id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    User save(User user);
}
