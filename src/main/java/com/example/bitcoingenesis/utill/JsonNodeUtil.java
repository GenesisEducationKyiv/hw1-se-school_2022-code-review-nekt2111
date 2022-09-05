package com.example.bitcoingenesis.utill;

import com.example.bitcoingenesis.model.Currency;
import com.fasterxml.jackson.databind.JsonNode;

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

}
