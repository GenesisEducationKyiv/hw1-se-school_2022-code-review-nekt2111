package com.example.bitcoingenesis.controller;

import com.example.bitcoingenesis.service.email.EmailValidationService;
import com.example.bitcoingenesis.service.email.SubscriptionEmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscribe")
public class SubscribeEmailController {

    private final SubscriptionEmailService subscriptionEmailService;
    private final EmailValidationService emailValidationService;

    public SubscribeEmailController(SubscriptionEmailService subscriptionEmailService,
                                    EmailValidationService emailValidationService) {
        this.subscriptionEmailService = subscriptionEmailService;
        this.emailValidationService = emailValidationService;
    }

    @PostMapping
    public ResponseEntity<Void> subscribe(@RequestParam String email) {

        if (!emailValidationService.validate(email)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (subscriptionEmailService.isSubscribed(email)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        subscriptionEmailService.subscribe(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
