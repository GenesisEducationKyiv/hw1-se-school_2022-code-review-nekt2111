package com.example.bitcoingenesis.utill;

import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.model.PriceInCurrency;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class CryptocurrencyShortPriceInfoDeserializer extends JsonDeserializer<CryptoPriceInfo> {

    @Override
    public CryptoPriceInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException  {

        CryptoPriceInfo cryptoPriceInfo = new CryptoPriceInfo();

        ObjectCodec codec = jsonParser.getCodec();
        JsonNode jsonNode = codec.readTree(jsonParser);

        cryptoPriceInfo.setCryptocurrencyName(getDeserializedCryptocurrencyName(jsonNode));

        JsonNodeUtil.getFirstChild(jsonNode).ifPresent(node -> setPriceInCurrencyValueForCrypto(node, cryptoPriceInfo));

        return cryptoPriceInfo;
    }

    private void setPriceInCurrencyValueForCrypto(JsonNode jsonNode, CryptoPriceInfo cryptoPriceInfo) {
        PriceInCurrency priceInCurrency = new PriceInCurrency();
        priceInCurrency.setCurrency(getDeserializedPriceCurrency(jsonNode));
        priceInCurrency.setPrice(getDeserializedPriceValue(jsonNode));

        cryptoPriceInfo.setPriceInCurrency(priceInCurrency);
    }

    private String getDeserializedCryptocurrencyName(JsonNode node) {
        return JsonNodeUtil.getFirstKeyAsString(node);
    }

    private Currency getDeserializedPriceCurrency(JsonNode node) {
        return JsonNodeUtil.getFirstKeyAsCurrency(node);
    }

    private Integer getDeserializedPriceValue(JsonNode node) {
        return JsonNodeUtil.getFirstValueAsInteger(node);
    }
}
