package com.example.bitcoingenesis.model;

import com.example.bitcoingenesis.utill.CryptocurrencyShortPriceInfoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(using = CryptocurrencyShortPriceInfoDeserializer.class)
public class CryptoPriceInfo {
    private String cryptocurrencyName;
    private PriceInCurrency priceInCurrency;

    public static CryptoPriceInfo createCryptoPriceInfo(String cryptocurrencyName, Currency currency, Integer price) {
        CryptoPriceInfo shortPriceInfo = new CryptoPriceInfo();
        shortPriceInfo.setCryptocurrencyName(cryptocurrencyName);

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
