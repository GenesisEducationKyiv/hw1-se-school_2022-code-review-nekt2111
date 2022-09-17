package com.example.bitcoingenesis.services;

import com.example.bitcoingenesis.repo.SubscriberEmailRepository;
import com.example.bitcoingenesis.service.email.SubscriptionEmailService;
import com.example.bitcoingenesis.service.email.SubscriptionEmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.bitcoingenesis.util.TestConstants.EMAIL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SubscriptionEmailServiceTest {

    private SubscriptionEmailService subscriptionEmailService;

    @Mock
    private SubscriberEmailRepository subscriberEmailRepository;

    @BeforeEach
    public void beforeTests() {
        this.subscriptionEmailService = new SubscriptionEmailServiceImpl(subscriberEmailRepository);
    }

    @Test
    public void subscribe() {
        subscriptionEmailService.subscribe(EMAIL);

        verify(subscriberEmailRepository).insert(EMAIL);
    }

    @Test
    public void unsubscribe() {
        subscriptionEmailService.unsubscribe(EMAIL);

        verify(subscriberEmailRepository).delete(EMAIL);
    }

    @Test
    public void updateSubscribedEmail() {
        String newEmail = "test@gmail.com";

        subscriptionEmailService.updateSubscribedEmail(EMAIL, newEmail);

        verify(subscriberEmailRepository).update(EMAIL, newEmail);
    }

    @Test
    public void getAllSubscribedEmails() {
        when(subscriberEmailRepository.findAll()).thenReturn(List.of(EMAIL));

        List<String> subscribedEmails = subscriptionEmailService.getAllSubscribedEmails();

        assertNotEquals(0, subscribedEmails.size());
    }

    @Test
    public void isSubscribedTrue() {
        when(subscriberEmailRepository.findByName(EMAIL)).thenReturn(EMAIL);

        boolean isSubscribed = subscriptionEmailService.isSubscribed(EMAIL);

        assertTrue(isSubscribed);
    }

    @Test
    public void isSubscribedFalse() {
        when(subscriberEmailRepository.findByName(EMAIL)).thenReturn(null);

        boolean isSubscribed = subscriptionEmailService.isSubscribed(EMAIL);

        assertFalse(isSubscribed);
    }
}
