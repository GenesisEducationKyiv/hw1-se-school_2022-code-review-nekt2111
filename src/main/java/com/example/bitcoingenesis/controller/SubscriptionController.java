package com.example.bitcoingenesis.controller;

import com.example.bitcoingenesis.service.SubscriptionEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionEmailService subscriptionEmailService;
    public SubscriptionController(SubscriptionEmailService subscriptionEmailService){
        this.subscriptionEmailService = subscriptionEmailService;
    }

    private final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    @DeleteMapping
    public ResponseEntity<Void> unsubscribe(@RequestParam String email) {

        if(!subscriptionEmailService.isSubscribed(email)) {
            logger.info("Email - {} is not subscribed", email);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        subscriptionEmailService.unsubscribe(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<String>> subscriptions(){
        logger.info("Getting all subscribed emails");
        List<String> emails =  subscriptionEmailService.getAllSubscribedEmails();
        logger.info("All subscribed emails - {}", emails);
        return ResponseEntity.ok(emails);
    }

}
