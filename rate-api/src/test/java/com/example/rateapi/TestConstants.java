package com.example.rateapi;

import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.CryptoPriceInfo;
import com.example.rateapi.model.Currency;
import com.example.rateapi.model.PriceInCurrency;

public class TestConstants {
    public static final Currency CURRENCY = Currency.UAH;
    public static final int PRICE = 100_000;
    public static final Crypto CRYPTO = Crypto.BTC;

    public static final String CRYPTO_CURRENCY_CLIENT_URL = "TEST_URL";

    public static final CryptoPriceInfo SHORT_PRICE_INFO = generateShortPriceInfo();

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
