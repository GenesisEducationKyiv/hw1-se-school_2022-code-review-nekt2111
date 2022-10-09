package com.example.subscriptionapi.service;

import com.example.subscriptionapi.model.User;

import java.util.List;

public interface SubscriptionUserService {

    void subscribe(User user);

    void unsubscribe(User user);

    List<String> getAllSubscribedUsersEmails();

    boolean isUserEmailSubscribed(String email);

    User findByEmail(String email);

}
