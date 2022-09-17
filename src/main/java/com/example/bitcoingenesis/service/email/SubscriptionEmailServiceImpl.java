package com.example.bitcoingenesis.service.email;

import com.example.bitcoingenesis.repo.SubscriberEmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionEmailServiceImpl implements SubscriptionEmailService {

    private final SubscriberEmailRepository subscriberEmailRepository;

    public SubscriptionEmailServiceImpl(SubscriberEmailRepository subscriberEmailRepository) {
        this.subscriberEmailRepository = subscriberEmailRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionEmailService.class);

    @Override
    public void subscribe(String email) {
        subscriberEmailRepository.insert(email);
        LOGGER.info("Subscribed an email - {}", email);
    }

    @Override
    public void unsubscribe(String email) {
        subscriberEmailRepository.delete(email);
        LOGGER.info("Unsubscribed an email - {}", email);
    }

    @Override
    public void updateSubscribedEmail(String oldEmail, String newEmail) {
        subscriberEmailRepository.update(oldEmail, newEmail);
        LOGGER.info("Updated subscribed email from - {} to - {}", oldEmail, newEmail);
    }

    @Override
    public List<String> getAllSubscribedEmails() {
        LOGGER.info("Getting all subscribed emails...");
        return subscriberEmailRepository.findAll();
    }

    @Override
    public boolean isSubscribed(String email) {
        LOGGER.info("Email - {} is already subscribed", email);
        return subscriberEmailRepository.findByName(email) != null;
    }
}
