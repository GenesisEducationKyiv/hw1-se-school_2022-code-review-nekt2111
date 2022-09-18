package com.example.bitcoingenesis.service.email;

import com.example.bitcoingenesis.service.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean sendEmail(SimpleMailMessage message) {
        LOGGER.info("Trying to send an email to {} with subject - {} and message - {}", message.getTo(), message.getSubject(), message.getText());
        boolean isSendingSuccessful = false;

        try {
            mailSender.send(message);
            LOGGER.info("Email was sent successfully");
            isSendingSuccessful = true;
        } catch (RuntimeException exception) {
            LOGGER.error("Email wasn't sent. Error occurred: ", exception);
        }
        return isSendingSuccessful;
    }

    @Override
    public boolean sendEmailToAll(SimpleMailMessage message, List<String> emails) {
        LOGGER.info("Starting to send messages to emails... Number of emails to send message - {}", emails.size());
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

        LOGGER.info("Sending of emails is completed.");
        LOGGER.info("Number of emails that were sent successfully - {} from {}. Emails - [{}] ", emailsWithSuccessfulSending.size(), emails.size(), emailsWithSuccessfulSending);
        LOGGER.info("Number of emails that were sent with failure - {} from {}. Emails - [{}] ", emailsWithFailedSending.size(), emails.size(), emailsWithFailedSending);

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
