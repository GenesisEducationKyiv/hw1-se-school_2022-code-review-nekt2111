package com.example.bitcoingenesis.services;

import com.example.bitcoingenesis.repo.SubscriberEmailDao;
import com.example.bitcoingenesis.service.SubscriptionEmailService;
import com.example.bitcoingenesis.service.SubscriptionEmailServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.example.bitcoingenesis.util.TestConstants.EMAIL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionEmailServiceTest {

    private SubscriptionEmailService subscriptionEmailService;

    @Mock
    private SubscriberEmailDao subscriberEmailDao;

    @Before
    public void beforeTest() {
        this.subscriptionEmailService = new SubscriptionEmailServiceImpl(subscriberEmailDao);
    }

    @Test
    public void subscribe() {
        subscriptionEmailService.subscribe(EMAIL);

        verify(subscriberEmailDao).insert(EMAIL);
    }

    @Test
    public void unsubscribe() {
        subscriptionEmailService.unsubscribe(EMAIL);

        verify(subscriberEmailDao).delete(EMAIL);
    }

    @Test
    public void updateSubscribedEmail() {
        String newEmail = "test@gmail.com";

        subscriptionEmailService.updateSubscribedEmail(EMAIL, newEmail);

        verify(subscriberEmailDao).update(EMAIL, newEmail);
    }

    @Test
    public void getAllSubscribedEmails() {
        when(subscriberEmailDao.findAll()).thenReturn(List.of(EMAIL));

        List<String> subscribedEmails = subscriptionEmailService.getAllSubscribedEmails();

        assertNotEquals(0, subscribedEmails.size());
    }

    @Test
    public void isSubscribedTrue() {
        when(subscriberEmailDao.findByName(EMAIL)).thenReturn(EMAIL);

        boolean isSubscribed = subscriptionEmailService.isSubscribed(EMAIL);

        assertTrue(isSubscribed);
    }

    @Test
    public void isSubscribedFalse() {
        when(subscriberEmailDao.findByName(EMAIL)).thenReturn(null);

        boolean isSubscribed = subscriptionEmailService.isSubscribed(EMAIL);

        assertFalse(isSubscribed);
    }
}
