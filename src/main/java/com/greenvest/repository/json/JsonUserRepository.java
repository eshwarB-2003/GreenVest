package com.greenvest.repository.json;

import com.greenvest.common.JsonFileUtil;
import com.greenvest.model.User;
import com.greenvest.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonUserRepository extends AbstractJsonRepository<User> implements UserRepository {

    public JsonUserRepository(JsonFileUtil fileUtil) {
        super(fileUtil, "users.json", User[].class);
    }

    @Override
    public Optional<User> findById(String id) {
        return readAllInternal().stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return readAllInternal().stream()
                .filter(u -> username.equalsIgnoreCase(u.getUsername()))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(readAllInternal());
    }

    @Override
    public User save(User user) {
        List<User> all = readAllInternal();
        all.removeIf(u -> u.getId().equals(user.getId()));
        all.add(user);
        writeAllInternal(all);
        return user;
    }
}
