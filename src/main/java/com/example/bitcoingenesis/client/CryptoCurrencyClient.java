package com.example.bitcoingenesis.client;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;

public interface CryptoCurrencyClient {

    Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency);

}
