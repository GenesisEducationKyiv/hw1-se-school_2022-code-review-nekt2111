package com.example.bitcoingenesis.util;

import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.model.PriceInCurrency;
import org.springframework.mail.SimpleMailMessage;

public final class TestConstants {

    public static final String EMAIL = "nekt2111@gmail.com";
    public static final SimpleMailMessage SIMPLE_MAIL_MESSAGE = generateSimpleMailMessage();
    public static final String SERVER_EMAIL = "genesisbitcoin@ukr.net";
    public static final Currency CURRENCY = Currency.UAH;
    public static final int PRICE = 100_000;
    public static final Crypto CRYPTO = Crypto.BTC;

    public static final String CRYPTO_CURRENCY_CLIENT_URL = "TEST_URL";

    public final static String MOCK_FILE_DB_LOCATION = "/usr/src/app/src/mock-db.txt";;

    public static final CryptoPriceInfo SHORT_PRICE_INFO = generateShortPriceInfo();


    private static SimpleMailMessage generateSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(SERVER_EMAIL);
        return simpleMailMessage;
    }

    private static CryptoPriceInfo generateShortPriceInfo() {
        CryptoPriceInfo cryptoPriceInfo = new CryptoPriceInfo();
        cryptoPriceInfo.setCrypto(CRYPTO);

        PriceInCurrency priceInCurrency = new PriceInCurrency();
        priceInCurrency.setPrice(PRICE);
        priceInCurrency.setCurrency(CURRENCY);

        cryptoPriceInfo.setPriceInCurrency(priceInCurrency);

        return cryptoPriceInfo;
    }
}
