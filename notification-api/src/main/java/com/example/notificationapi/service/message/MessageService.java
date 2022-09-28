package com.example.notificationapi.service.message;


import com.example.notificationapi.model.rate.CryptoPriceInfo;
import org.springframework.mail.SimpleMailMessage;

public interface MessageService {

    SimpleMailMessage createPriceMessageFromCryptoPriceInfo(CryptoPriceInfo cryptoPriceInfo, String fromEmail);

    SimpleMailMessage createPriceMessage(Integer price, String fromEmail);

    SimpleMailMessage createGreetingMessage(String fromEmail);

}
