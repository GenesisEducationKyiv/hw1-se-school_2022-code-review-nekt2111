package com.example.bitcoingenesis.service.rate.providers.exceptional.kucoin;

import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.example.bitcoingenesis.model.PriceInCurrency;
import com.example.bitcoingenesis.utill.JsonNodeUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Optional;

public class KucoinFiatPriceDeserializer extends JsonDeserializer<CryptoPriceInfo> {

    @Override
    public CryptoPriceInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        CryptoPriceInfo cryptoPriceInfo = new CryptoPriceInfo();

        ObjectCodec codec = jsonParser.getCodec();
        JsonNode jsonNode = codec.readTree(jsonParser);

        Optional<JsonNode> jsonDataNode = JsonNodeUtil.findJsonNodeByKeyName(jsonNode, "data");

        jsonDataNode.ifPresent(node -> setPriceInCurrencyValueForCrypto(node, cryptoPriceInfo));
        jsonDataNode.ifPresent(node -> cryptoPriceInfo.setCrypto(JsonNodeUtil.getDeserializedCryptoFromShortName(node)));

        return cryptoPriceInfo;
    }

    private void setPriceInCurrencyValueForCrypto(JsonNode jsonNode, CryptoPriceInfo cryptoPriceInfo) {
        PriceInCurrency priceInCurrency = new PriceInCurrency();
        priceInCurrency.setPrice(JsonNodeUtil.getDeserializedPriceValue(jsonNode));

        cryptoPriceInfo.setPriceInCurrency(priceInCurrency);
    }

}
