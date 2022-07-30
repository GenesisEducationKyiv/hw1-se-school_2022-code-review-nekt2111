package com.example.bitcoingenesis.service;

import java.util.List;

public interface SubscriptionEmailService {

    void subscribe(String email);

    void unsubscribe(String email);

    void updateSubscribedEmail(String oldEmail, String newEmail);

    List<String> getAllSubscribedEmails();

    boolean isSubscribed(String email);

}
