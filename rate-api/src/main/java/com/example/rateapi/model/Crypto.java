package com.example.rateapi.model;

public enum Crypto {
    BTC("bitcoin"),
    ETH("ethereum"),
    SOL("solana");

    private final String fullName;

    Crypto(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public static Crypto fromFullName(String fullName) {
        for (Crypto crypto : Crypto.values()) {
            if (crypto.fullName.equalsIgnoreCase(fullName)) {
                return crypto;
            }
        }
        return null;
    }
}
