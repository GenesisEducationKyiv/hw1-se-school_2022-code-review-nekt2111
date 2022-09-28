package com.example.rateapi.service;

import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.Currency;

public interface CryptoRateService {

    Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency);

}
