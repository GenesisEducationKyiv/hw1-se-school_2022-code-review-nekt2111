package com.example.bitcoingenesis.service;

import com.example.bitcoingenesis.model.CryptoPriceInfo;
import org.springframework.mail.SimpleMailMessage;

public interface MessageService {

    SimpleMailMessage createPriceMessageFromCryptoPriceInfo(CryptoPriceInfo cryptoPriceInfo, String fromEmail);

    SimpleMailMessage createPriceMessage(Integer price, String fromEmail);

    SimpleMailMessage createGreetingMessage(String fromEmail);

}
