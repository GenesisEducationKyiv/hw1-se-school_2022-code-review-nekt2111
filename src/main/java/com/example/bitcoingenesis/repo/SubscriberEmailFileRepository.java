package com.example.bitcoingenesis.repo;

import com.example.bitcoingenesis.utill.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscriberEmailFileRepository implements SubscriberEmailRepository {

    @Value("${db.file.path}")
    private String fileLocation;
    private static final String EMAILS_STRING_SEPARATOR = " ";

    @Override
    public void insert(String email) {
        FileUtil.appendStringToEndOfFirstLine(email, fileLocation, EMAILS_STRING_SEPARATOR);
    }

    @Override
    public void update(String oldEmail, String newEmail) {
        List<String> emails = findAll();

        boolean emailExists = emails.stream()
                .anyMatch(str -> str.equals(oldEmail));

        if (emailExists) {
            emails.add(emails.indexOf(oldEmail), newEmail);
            FileUtil.writeInFirstLineStringFromList(emails, fileLocation, EMAILS_STRING_SEPARATOR);
        }

    }

    @Override
    public void delete(String email) {
        List<String> emails = findAll();

        boolean emailExists = emails.stream()
                .anyMatch(str -> str.equals(email));

        if (emailExists) {
            emails.remove(email);
            FileUtil.writeInFirstLineStringFromList(emails, fileLocation, EMAILS_STRING_SEPARATOR);
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
        return FileUtil.getFileFirstLineAsListOfStrings(fileLocation, EMAILS_STRING_SEPARATOR);
    }
}
