package com.example.bitcoingenesis.repo;

import java.util.List;

public interface SubscriberEmailRepository {

    void insert(String email);

    void update(String oldEmail, String newEmail);

    void delete(String subscriberEmail);

    String findByName(String email);

    List<String> findAll();

}
