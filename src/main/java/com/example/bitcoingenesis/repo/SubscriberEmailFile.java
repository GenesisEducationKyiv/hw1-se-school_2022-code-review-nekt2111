package com.example.bitcoingenesis.repo;

import com.example.bitcoingenesis.utill.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class SubscriberEmailFile implements SubscriberEmailDao {

    private static final String FILE_LOCATION = "src/db.txt";
    private static final String EMAILS_STRING_SEPARATOR = " ";

    @Override
    public void insert(String email) {
        FileUtil.appendStringToEndOfFirstLine(email, FILE_LOCATION, EMAILS_STRING_SEPARATOR);
    }

    @Override
    public void update(String oldEmail, String newEmail) {
        List<String> emails = findAll();

        boolean emailExists = emails.stream()
                .anyMatch(str -> str.equals(oldEmail));

        if (emailExists) {
            emails.add(emails.indexOf(oldEmail), newEmail);
            FileUtil.writeInFirstLineStringFromList(emails, FILE_LOCATION, EMAILS_STRING_SEPARATOR);
        }

    }

    @Override
    public void delete(String email) {
        List<String> emails = findAll();

        boolean emailExists = emails.stream()
                .anyMatch(str -> str.equals(email));

        if (emailExists) {
            emails.remove(email);
            FileUtil.writeInFirstLineStringFromList(emails, FILE_LOCATION, EMAILS_STRING_SEPARATOR);
        }
    }

    @Override
    public String findByName(String email) {
        return findAll().stream()
                .filter(str -> str.equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<String> findAll() {
        return FileUtil.getFileFirstLineAsListOfStrings(FILE_LOCATION, EMAILS_STRING_SEPARATOR);
    }
}
