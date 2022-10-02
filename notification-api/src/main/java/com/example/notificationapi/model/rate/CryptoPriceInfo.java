package com.example.notificationapi.model.rate;

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

    public void setPrice(Integer price) {
        priceInCurrency.setPrice(price);
    }

    public void setCurrency(Currency currency) {
        priceInCurrency.setCurrency(currency);
    }
}
