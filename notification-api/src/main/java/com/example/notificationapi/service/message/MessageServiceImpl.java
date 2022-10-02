package com.example.notificationapi.service.message;

import com.example.notificationapi.model.rate.CryptoPriceInfo;
import com.example.notificationapi.model.rate.Currency;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public SimpleMailMessage createPriceMessageFromCryptoPriceInfo(CryptoPriceInfo cryptoPriceInfo, String fromEmail) {
        String cryptoName = cryptoPriceInfo.getCrypto().getFullName().toUpperCase();

        String messageSubject = createSubjectForPriceMessage(cryptoName, cryptoPriceInfo.getCurrency());
        String messageText = createTextForPriceMessage(cryptoName, cryptoPriceInfo.getPrice(), cryptoPriceInfo.getCurrency());

        return createSimpleMessage(fromEmail, messageSubject, messageText);
    }

    @Override
    public SimpleMailMessage createPriceMessage(Integer price, String fromEmail) {

        String messageSubject = createSubjectForPriceMessage("bitcoin", Currency.UAH);
        String messageText = createTextForPriceMessage("bitcoin", price, Currency.UAH);

        return createSimpleMessage(fromEmail, messageSubject, messageText);
    }

    @Override
    public SimpleMailMessage createGreetingMessage(String fromEmail) {
        return createSimpleMessage(fromEmail, "Hello!", "We are glad that you are with us!");
    }

    private static SimpleMailMessage createSimpleMessage(String fromEmail, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);

        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        return simpleMailMessage;
    }

    private static String createSubjectForPriceMessage(String cryptoName, Currency currency) {
        return String.format("%s rate in %s", cryptoName, currency);
    }

    private static String createTextForPriceMessage(String cryptoName, Integer price, Currency currency) {
        return String.format("1 %s = %d %s (%s)", cryptoName, price, currency, currency.getFullName());
    }
}
