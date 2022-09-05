package com.example.bitcoingenesis.service;

import com.example.bitcoingenesis.model.CryptocurrencyShortPriceInfo;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public interface EmailService {

    boolean sendEmail(SimpleMailMessage message);

    void sendEmailToAll(SimpleMailMessage message, List<String> emails);

    SimpleMailMessage createMessageFromCryptocurrencyShortPriceInfo(CryptocurrencyShortPriceInfo cryptocurrencyShortPriceInfo);

}
