package com.example.userapi.service;

import com.example.userapi.service.logger.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

@Service
public class EmailValidationService {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private final LoggerService loggerService;

    public EmailValidationService(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    public boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);

        boolean result = matcher.find();

        if (!result) {
            loggerService.logError(format("Email - %s is not valid", emailStr));
        }

        return result;
    }

}
