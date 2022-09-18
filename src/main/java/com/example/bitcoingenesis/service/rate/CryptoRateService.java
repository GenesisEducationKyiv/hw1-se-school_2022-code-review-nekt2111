package com.example.bitcoingenesis.service.rate;

import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;

public interface CryptoRateService {

    Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency);

}
