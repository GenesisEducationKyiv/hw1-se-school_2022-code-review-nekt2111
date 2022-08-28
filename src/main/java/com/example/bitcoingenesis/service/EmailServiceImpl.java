package com.example.bitcoingenesis.service;

import com.example.bitcoingenesis.model.CryptocurrencyShortPriceInfo;
import com.example.bitcoingenesis.model.PriceInCurrency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

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
    public void sendEmailToAll(SimpleMailMessage message, List<String> emails) {
        LOGGER.info("Starting to send messages to emails... Number of emails to send message - {}", emails.size());

        Map<String, Boolean> emailsResultOfSendingMap = new HashMap<>();

        for (String email : emails) {
            message.setTo(email);
            emailsResultOfSendingMap.put(email, sendEmail(message));
        }

        List<String> emailsWithSuccessfulSending = getAllEmailsWithSpecificResultOfSending(emailsResultOfSendingMap, true);
        List<String> emailsWithFailedSending = getAllEmailsWithSpecificResultOfSending(emailsResultOfSendingMap, false);

        LOGGER.info("Sending of emails is completed.");
        LOGGER.info("Number of emails that were sent successfully - {} from {}. Emails - [{}] ", emailsWithSuccessfulSending.size(), emails.size(), emailsWithSuccessfulSending);
        LOGGER.info("Number of emails that were sent with failure - {} from {}. Emails - [{}] ", emailsWithFailedSending.size(), emails.size(), emailsWithFailedSending);
    }

    @Override
    public SimpleMailMessage createMessageFromCryptocurrencyShortPriceInfo(CryptocurrencyShortPriceInfo cryptoPriceInfo) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("genesisbitcoinrate@ukr.net");

        String cryptoName = cryptoPriceInfo.getCryptocurrencyName().toUpperCase();
        PriceInCurrency cryptoPrice = cryptoPriceInfo.getPriceInCurrency();

        simpleMailMessage.setSubject(String.format("%s rate", cryptoName));
        simpleMailMessage.setText(String.format("1 %s = %d %s (%s)", cryptoName, cryptoPrice.getPrice(), cryptoPrice.getCurrency(), cryptoPrice.getCurrency().getName()));
        return simpleMailMessage;
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
