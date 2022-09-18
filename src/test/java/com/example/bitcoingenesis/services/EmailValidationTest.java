package com.example.bitcoingenesis.services;

import com.example.bitcoingenesis.service.email.EmailValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.bitcoingenesis.util.TestConstants.EMAIL;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmailValidationTest {
    private EmailValidationService emailValidationService;

    @BeforeEach
    public void beforeTests(){
        this.emailValidationService = new EmailValidationService();
    }

    @Test
    public void validateEmailWithoutAtSymbol(){
        String email = "nekt2111gmail.com";

        boolean isEmailValid = emailValidationService.validate(email);

        assertFalse(isEmailValid);
    }

    @Test
    public void validateEmailWithoutDotSymbol(){
        String email = "nekt2111@gmailcom";

        boolean isEmailValid = emailValidationService.validate(email);

        assertFalse(isEmailValid);
    }

    @Test
    public void validateEmailWithoutCom(){
        String email = "nekt2111@gmail.";

        boolean isEmailValid = emailValidationService.validate(email);

        assertFalse(isEmailValid);
    }

    @Test
    public void validateEmptyEmail() {
        String email = "@gmail.com";

        boolean isEmailValid = emailValidationService.validate(email);

        assertFalse(isEmailValid);
    }

    @Test
    public void validateEmail(){
        boolean isEmailValid = emailValidationService.validate(EMAIL);

        assertTrue(isEmailValid);
    }
}
