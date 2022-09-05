package com.example.bitcoingenesis.services;

import com.example.bitcoingenesis.service.EmailValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.bitcoingenesis.util.TestConstants.EMAIL;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailValidationTest {
    private EmailValidationService emailValidationService;

    @Before
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
