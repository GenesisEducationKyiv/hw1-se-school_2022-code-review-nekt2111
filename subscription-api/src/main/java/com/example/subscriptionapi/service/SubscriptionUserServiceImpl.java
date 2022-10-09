package com.example.subscriptionapi.service;

import com.example.subscriptionapi.model.User;
import com.example.subscriptionapi.repo.SubscriberUserRepository;
import com.example.subscriptionapi.service.logger.LoggerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class SubscriptionUserServiceImpl implements SubscriptionUserService {

    private final SubscriberUserRepository subscriberUserRepository;

    private final LoggerService loggerService;


    public SubscriptionUserServiceImpl(SubscriberUserRepository subscriberUserRepository,
                                       LoggerService loggerService,
                                       RabbitMqEmitter rabbitMqEmitter) {
        this.subscriberUserRepository = subscriberUserRepository;
        this.loggerService = loggerService;
        this.loggerService.setOutputClassName(this.getClass().getName());
    }


    @Override
    public void subscribe(User user) {
        subscriberUserRepository.insert(user);
        String email = user.getEmail();
        loggerService.logInfo(format("Subscribed a user - %s", user));
    }

    @Override
    public void unsubscribe(User user) {
        subscriberUserRepository.delete(user);
        loggerService.logInfo(format("Unsubscribed an email - %s", user));
    }

    @Override
    public List<String> getAllSubscribedUsersEmails() {
        loggerService.logInfo("Getting all subscribed emails...");
        return subscriberUserRepository.findAll().stream().map(User::getEmail).collect(Collectors.toList());
    }

    @Override
    public boolean isUserEmailSubscribed(String email) {
        loggerService.logInfo(format("User with email - %s is already subscribed", email));
        return subscriberUserRepository.findByEmail(email) != null;
    }

    @Override
    public User findByEmail(String email) {
        return subscriberUserRepository.findByEmail(email);
    }
}
