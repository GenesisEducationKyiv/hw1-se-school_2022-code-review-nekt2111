package com.example.bitcoingenesis.service;

import com.example.bitcoingenesis.repo.SubscriberEmailDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionEmailServiceImpl implements SubscriptionEmailService {

    private final SubscriberEmailDao subscriberEmailDao;

    public SubscriptionEmailServiceImpl(SubscriberEmailDao subscriberEmailDao) {
        this.subscriberEmailDao = subscriberEmailDao;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionEmailService.class);

    @Override
    public void subscribe(String email) {
        subscriberEmailDao.insert(email);
        LOGGER.info("Subscribed an email - {}", email);
    }

    @Override
    public void unsubscribe(String email) {
        subscriberEmailDao.delete(email);
        LOGGER.info("Unsubscribed an email - {}", email);
    }

    @Override
    public void updateSubscribedEmail(String oldEmail, String newEmail) {
        subscriberEmailDao.update(oldEmail, newEmail);
        LOGGER.info("Updated subscribed email from - {} to - {}", oldEmail, newEmail);
    }

    @Override
    public List<String> getAllSubscribedEmails() {
        LOGGER.info("Getting all subscribed emails");
        List<String> emails = subscriberEmailDao.findAll();
        LOGGER.info("All subscribed emails - {}", emails);
        return emails;
    }

    @Override
    public boolean isSubscribed(String email) {
        LOGGER.info("Email - {} is already subscribed", email);
        return subscriberEmailDao.findByName(email) != null;
    }
}
