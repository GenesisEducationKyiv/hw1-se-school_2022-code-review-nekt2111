package com.example.notificationapi.model.rate;

public enum Currency {
    USD("United States dollar"),
    UAH("Ukrainian grivna"),

    PLN("Polska zlota");

    private final String fullName;

    Currency(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
