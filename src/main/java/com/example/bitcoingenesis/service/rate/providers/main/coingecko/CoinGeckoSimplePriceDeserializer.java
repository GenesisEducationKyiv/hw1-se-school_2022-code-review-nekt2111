package com.example.bitcoingenesis.service.rate.providers.main.coingecko;

import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.example.bitcoingenesis.model.PriceInCurrency;
import com.example.bitcoingenesis.utill.JsonNodeUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class CoinGeckoSimplePriceDeserializer extends JsonDeserializer<CryptoPriceInfo> {

    @Override
    public CryptoPriceInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException  {

        CryptoPriceInfo cryptoPriceInfo = new CryptoPriceInfo();

        ObjectCodec codec = jsonParser.getCodec();
        JsonNode jsonNode = codec.readTree(jsonParser);

        cryptoPriceInfo.setCrypto(JsonNodeUtil.getDeserializedCryptoFromFullName(jsonNode));

        JsonNodeUtil.getFirstChild(jsonNode).ifPresent(node -> setPriceInCurrencyValueForCrypto(node, cryptoPriceInfo));

        return cryptoPriceInfo;
    }

    private void setPriceInCurrencyValueForCrypto(JsonNode jsonNode, CryptoPriceInfo cryptoPriceInfo) {
        PriceInCurrency priceInCurrency = new PriceInCurrency();
        priceInCurrency.setCurrency(JsonNodeUtil.getDeserializedPriceCurrency(jsonNode));
        priceInCurrency.setPrice(JsonNodeUtil.getDeserializedPriceValue(jsonNode));

        cryptoPriceInfo.setPriceInCurrency(priceInCurrency);
    }

}
