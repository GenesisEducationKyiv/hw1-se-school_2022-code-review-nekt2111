package com.example.notificationapi;

import com.example.notificationapi.model.rate.Crypto;
import com.example.notificationapi.model.rate.Currency;
import org.springframework.mail.SimpleMailMessage;

public class TestConstants {
    public static final String EMAIL = "nekt2111@gmail.com";
    public static final SimpleMailMessage SIMPLE_MAIL_MESSAGE = generateSimpleMailMessage();
    public static final String SERVER_EMAIL = "genesisbitcoin@ukr.net";

    public static final Currency CURRENCY = Currency.UAH;
    public static final int PRICE = 100_000;
    public static final Crypto CRYPTO = Crypto.BTC;

    private static SimpleMailMessage generateSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(SERVER_EMAIL);
        return simpleMailMessage;
    }
}
