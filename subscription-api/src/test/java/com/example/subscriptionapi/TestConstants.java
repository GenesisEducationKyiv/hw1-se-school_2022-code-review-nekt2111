package com.example.subscriptionapi;

import com.example.subscriptionapi.model.User;

public class TestConstants {
    public static final String EMAIL = "nekt2111@gmail.com";
    public static final String MOCK_FILE_DB_LOCATION = "/usr/src/app/src/mock-db.txt";

    public static final User USER = generateUser();

    private static User generateUser() {
        return new User(EMAIL);
    }
}
