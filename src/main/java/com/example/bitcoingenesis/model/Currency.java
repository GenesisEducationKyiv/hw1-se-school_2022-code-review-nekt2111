package com.example.bitcoingenesis.model;

public enum Currency {
    USD("United States dollar"),
    UAH("Ukrainian grivna"),

    PLN("Polska zlota");

    private final String name;

    Currency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
