package com.example.bitcoingenesis.service.rate.providers.main.coinbase;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName(value = "data")
public class CoinBaseSpotPriceResponse {

    @JsonProperty(value = "base")
    private String cryptocurrencyName;
    private String currency;
    @JsonProperty(value = "amount")
    private Float price;

}
