package com.example.subscriptionapi.controller;

import com.example.subscriptionapi.command.CreateUserSubscriptionCommand;
import com.example.subscriptionapi.model.User;
import com.example.subscriptionapi.service.EmailValidationService;
import com.example.subscriptionapi.service.RabbitMqEmitter;
import com.example.subscriptionapi.service.SubscriptionUserService;
import com.example.subscriptionapi.service.logger.LoggerService;
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

    private final RabbitMqEmitter rabbitMqEmitter;

    public SubscriptionController(SubscriptionUserService subscriptionUserService,
                                  EmailValidationService emailValidationService,
                                  LoggerService loggerService,
                                  RabbitMqEmitter rabbitMqEmitter) {
        this.subscriptionUserService = subscriptionUserService;
        this.emailValidationService = emailValidationService;
        this.loggerService = loggerService;
        this.rabbitMqEmitter = rabbitMqEmitter;
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
        rabbitMqEmitter.emit("subscribe-user", new CreateUserSubscriptionCommand(user));

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
