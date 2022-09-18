package com.example.bitcoingenesis.utill.cache;

import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class CryptoCurrencyPair {

    private Crypto crypto;
    private Currency currency;

}
