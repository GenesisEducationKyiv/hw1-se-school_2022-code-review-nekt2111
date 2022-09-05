package com.example.bitcoingenesis.util;

import com.example.bitcoingenesis.model.CryptocurrencyShortPriceInfo;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.model.PriceInCurrency;
import org.springframework.mail.SimpleMailMessage;

public final class TestConstants {

    public static final String EMAIL = "nekt2111@gmail.com";
    public static final SimpleMailMessage SIMPLE_MAIL_MESSAGE = generateSimpleMailMessage();
    public static final String SERVER_EMAIL = "genesisbitcoin@ukr.net";
    public static final Currency CURRENCY = Currency.UAH;
    public static final int PRICE = 100_000;
    public static final String CRYPTO = "BITCOIN";

    public static final CryptocurrencyShortPriceInfo  SHORT_PRICE_INFO = generateShortPriceInfo();


    private static SimpleMailMessage generateSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(SERVER_EMAIL);
        return simpleMailMessage;
    }

    private static CryptocurrencyShortPriceInfo generateShortPriceInfo() {
        CryptocurrencyShortPriceInfo cryptocurrencyShortPriceInfo = new CryptocurrencyShortPriceInfo();
        cryptocurrencyShortPriceInfo.setCryptocurrencyName(CRYPTO);

        PriceInCurrency priceInCurrency = new PriceInCurrency();
        priceInCurrency.setPrice(PRICE);
        priceInCurrency.setCurrency(CURRENCY);

        cryptocurrencyShortPriceInfo.setPriceInCurrency(priceInCurrency);

        return cryptocurrencyShortPriceInfo;
    }
}
