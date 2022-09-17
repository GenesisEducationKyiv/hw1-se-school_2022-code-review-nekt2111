package com.example.bitcoingenesis.model;

import lombok.Data;

@Data
public class CryptoPriceInfo {
    private Crypto crypto;
    private PriceInCurrency priceInCurrency;

    public static CryptoPriceInfo createCryptoPriceInfo(Crypto crypto, Currency currency, Integer price) {
        CryptoPriceInfo shortPriceInfo = new CryptoPriceInfo();
        shortPriceInfo.setCrypto(crypto);

        PriceInCurrency priceInCurrency = new PriceInCurrency();

        priceInCurrency.setCurrency(currency);
        priceInCurrency.setPrice(price);

        shortPriceInfo.setPriceInCurrency(priceInCurrency);

        return shortPriceInfo;
    }

    public Integer getPrice() {
        return priceInCurrency.getPrice();
    }

    public Currency getCurrency() {
        return priceInCurrency.getCurrency();
    }
}
