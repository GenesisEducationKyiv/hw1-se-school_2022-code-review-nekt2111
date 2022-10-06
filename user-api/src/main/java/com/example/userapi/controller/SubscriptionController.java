package com.example.userapi.controller;

import com.example.userapi.model.User;
import com.example.userapi.service.EmailValidationService;
import com.example.userapi.service.SubscriptionUserService;
import com.example.userapi.service.logger.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.String.format;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionUserService subscriptionUserService;

    private final EmailValidationService emailValidationService;

    private final LoggerService loggerService;

    public SubscriptionController(SubscriptionUserService subscriptionUserService,
                                  EmailValidationService emailValidationService,
                                  LoggerService loggerService) {
        this.subscriptionUserService = subscriptionUserService;
        this.emailValidationService = emailValidationService;
        this.loggerService = loggerService;
        this.loggerService.setOutputClassName(this.getClass().getName());
    }

    @PostMapping
    public ResponseEntity<Void> subscribe(@RequestParam String email) {

        if (!emailValidationService.validate(email)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (subscriptionUserService.isUserEmailSubscribed(email)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        User user = new User(email);
        subscriptionUserService.subscribe(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> unsubscribe(@RequestParam String email) {

        User user = subscriptionUserService.findByEmail(email);

        if (user == null) {
            loggerService.logError(format("User - %s is not subscribed", email));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        subscriptionUserService.unsubscribe(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/emails")
    public ResponseEntity<List<String>> emailsOfSubscribers() {
        List<String> emails = subscriptionUserService.getAllSubscribedUsersEmails();
        loggerService.logInfo(format("All subscribed emails - %s", emails));
        return ResponseEntity.ok(emails);
    }
}
