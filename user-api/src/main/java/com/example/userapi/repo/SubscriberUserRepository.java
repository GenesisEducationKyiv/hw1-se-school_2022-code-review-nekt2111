package com.example.userapi.repo;

import com.example.userapi.model.User;

import java.util.List;

public interface SubscriberUserRepository {

    void insert(User user);

    void update(String oldEmail, String newEmail);

    void delete(User user);

    User findByEmail(String email);

    List<User> findAll();

}
