package com.example.userapi.service;

import com.example.userapi.model.User;
import com.example.userapi.repo.SubscriberUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionUserServiceImpl implements SubscriptionUserService {

    private final SubscriberUserRepository subscriberUserRepository;

    public SubscriptionUserServiceImpl(SubscriberUserRepository subscriberUserRepository) {
        this.subscriberUserRepository = subscriberUserRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionUserService.class);

    @Override
    public void subscribe(User user) {
        subscriberUserRepository.insert(user);
        LOGGER.info("Subscribed a user - {}", user);
    }

    @Override
    public void unsubscribe(User user) {
        subscriberUserRepository.delete(user);
        LOGGER.info("Unsubscribed an email - {}", user);
    }

    @Override
    public List<String> getAllSubscribedUsersEmails() {
        LOGGER.info("Getting all subscribed emails...");
        return subscriberUserRepository.findAll().stream().map(User::getEmail).collect(Collectors.toList());
    }

    @Override
    public boolean isUserEmailSubscribed(String email) {
        LOGGER.info("User with email - {} is already subscribed", email);
        return subscriberUserRepository.findByEmail(email) != null;
    }

    @Override
    public User findByEmail(String email) {
        return subscriberUserRepository.findByEmail(email);
    }
}
