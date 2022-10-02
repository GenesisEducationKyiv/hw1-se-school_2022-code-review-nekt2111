package com.example.notificationapi.service.email;

import com.example.notificationapi.service.logger.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final LoggerService loggerService;

    public EmailServiceImpl(JavaMailSender mailSender,
                            LoggerService loggerService) {
        this.mailSender = mailSender;
        this.loggerService = loggerService;
        this.loggerService.setOutputClassName(this.getClass().getName());
    }

    @Override
    public boolean sendEmail(SimpleMailMessage message) {
        loggerService.logInfo(format("Trying to send an email to %s with subject - %s and message - %s", message.getTo(), message.getSubject(), message.getText()));
        boolean isSendingSuccessful = false;

        try {
            mailSender.send(message);
            loggerService.logInfo("Email was sent successfully");
            isSendingSuccessful = true;
        } catch (RuntimeException exception) {
            loggerService.logError(format("Email wasn't sent. Error occurred: %s", exception.getMessage()));
        }
        return isSendingSuccessful;
    }

    @Override
    public boolean sendEmailToAll(SimpleMailMessage message, List<String> emails) {
        loggerService.logInfo(format("Starting to send messages to emails... Number of emails to send message - %s", emails.size()));
        boolean sendingToAllSuccessful = false;

        Map<String, Boolean> emailsResultOfSendingMap = new HashMap<>();

        for (String email : emails) {
            message.setTo(email);
            emailsResultOfSendingMap.put(email, sendEmail(message));
        }

        List<String> emailsWithSuccessfulSending = getAllEmailsWithSpecificResultOfSending(emailsResultOfSendingMap, true);
        List<String> emailsWithFailedSending = getAllEmailsWithSpecificResultOfSending(emailsResultOfSendingMap, false);

        if (emailsWithFailedSending.isEmpty()) {
            sendingToAllSuccessful = true;
        }

        loggerService.logInfo("Sending of emails is completed.");
        loggerService.logInfo(format("Number of emails that were sent successfully - %s from %s. Emails - [%s] ", emailsWithSuccessfulSending.size(), emails.size(), emailsWithSuccessfulSending));
        loggerService.logInfo(format("Number of emails that were sent with failure - %s from %s. Emails - [%s] ", emailsWithFailedSending.size(), emails.size(), emailsWithFailedSending));

        return sendingToAllSuccessful;
    }

    private List<String> getAllEmailsWithSpecificResultOfSending(Map<String, Boolean> emailsResultOfSendingMap, boolean wasSuccessfullySent) {
        List<String> emails = new ArrayList<>();
        emailsResultOfSendingMap.forEach((k, val) -> {
            if (val.equals(wasSuccessfullySent)) {
                emails.add(k);
            }
        });
        return emails;
    }
}
