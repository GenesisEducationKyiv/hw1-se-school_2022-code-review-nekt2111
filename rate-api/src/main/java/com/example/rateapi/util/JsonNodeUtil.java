package com.example.rateapi.util;
import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.Currency;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public class JsonNodeUtil {

    public static Optional<JsonNode> getFirstChild(JsonNode jsonNode) {

        if (jsonNode.elements().hasNext()) {
            return Optional.of(jsonNode.elements().next());
        }
        return Optional.empty();
    }

    public static Integer getFirstValueAsInteger(JsonNode jsonNode) {
        if (jsonNode.elements().hasNext()) {
            return jsonNode.elements().next().asInt();
        }
        return null;
    }

    public static String getFirstKeyAsString(JsonNode jsonNode) {
        if (jsonNode.fieldNames().hasNext()) {
            return jsonNode.fieldNames().next();
        }
        return null;
    }

    public static Currency getFirstKeyAsCurrency(JsonNode jsonNode) {
        if (jsonNode.fieldNames().hasNext()) {
            return Currency.valueOf(jsonNode.fieldNames().next().toUpperCase());
        }
        return null;
    }

    public static Optional<JsonNode> findJsonNodeByKeyName(JsonNode jsonNode, String key) {

        Iterator<String> nodeIterator = jsonNode.fieldNames();
        Iterator<JsonNode> jsonNodeIterator = jsonNode.elements();

        while (nodeIterator.hasNext()) {
            String currentKeyName = nodeIterator.next();
            JsonNode currentJsonNode = jsonNodeIterator.next();

            if (Objects.equals(currentKeyName, key)) {
                return Optional.ofNullable(currentJsonNode);
            }
        }

        return Optional.empty();
    }

    public static Crypto getDeserializedCryptoFromFullName(JsonNode node) {
        return Crypto.fromFullName(JsonNodeUtil.getFirstKeyAsString(node));
    }

    public static Crypto getDeserializedCryptoFromShortName(JsonNode node) {
        return Crypto.valueOf(JsonNodeUtil.getFirstKeyAsString(node));
    }

    public static Currency getDeserializedPriceCurrency(JsonNode node) {
        return JsonNodeUtil.getFirstKeyAsCurrency(node);
    }

    public static Integer getDeserializedPriceValue(JsonNode node) {
        return JsonNodeUtil.getFirstValueAsInteger(node);
    }

}
