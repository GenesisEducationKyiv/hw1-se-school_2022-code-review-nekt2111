package com.example.rateapi.util.cache;

import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.Currency;
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
