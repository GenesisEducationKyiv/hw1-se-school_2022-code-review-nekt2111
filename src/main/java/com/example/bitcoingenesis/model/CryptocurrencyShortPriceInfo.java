package com.example.bitcoingenesis.model;

import com.example.bitcoingenesis.utill.CryptocurrencyShortPriceInfoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(using = CryptocurrencyShortPriceInfoDeserializer.class)
public class CryptocurrencyShortPriceInfo {
    private String cryptocurrencyName;
    private PriceInCurrency priceInCurrency;
}
