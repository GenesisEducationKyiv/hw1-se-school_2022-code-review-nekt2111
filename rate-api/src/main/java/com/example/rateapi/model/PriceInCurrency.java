package com.example.rateapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Util class to show price in specific currency
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceInCurrency {
    private Integer price;
    private Currency currency;
}
