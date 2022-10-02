package com.example.userapi.services;

import com.example.userapi.service.EmailValidationService;
import com.example.userapi.service.logger.LoggerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.userapi.TestConstants.EMAIL;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmailValidationTest {
    private EmailValidationService emailValidationService;

    @Mock
    private LoggerService loggerService;

    @BeforeEach
    public void beforeTests(){
        this.emailValidationService = new EmailValidationService(loggerService);
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