package com.example.subscriptionapi.repo;

import com.example.subscriptionapi.model.User;
import com.example.subscriptionapi.utill.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SubscriberUserFileRepository implements SubscriberUserRepository {

    @Value("${db.file.path}")
    private String fileLocation;
    private static final String EMAILS_STRING_SEPARATOR = " ";

    @Override
    public void insert(User user) {
        FileUtil.appendStringToEndOfFirstLine(user.getEmail(), fileLocation, EMAILS_STRING_SEPARATOR);
    }

    @Override
    public void update(String oldEmail, String newEmail) {
        List<User> users = findAll();
        List<String> emails = users.stream().map(User::getEmail).collect(Collectors.toList());

        boolean emailExists = emails.stream()
                .anyMatch(str -> str.equals(oldEmail));

        if (emailExists) {
            emails.add(emails.indexOf(oldEmail), newEmail);
            FileUtil.writeInFirstLineStringFromList(emails, fileLocation, EMAILS_STRING_SEPARATOR);
        }

    }

    @Override
    public void delete(User user) {
        List<User> users = findAll();

        boolean userExists = users.stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()));

        if (userExists) {
            users.remove(user);
            List<String> emails = users.stream().map(User::getEmail).collect(Collectors.toList());
            FileUtil.writeInFirstLineStringFromList(emails, fileLocation, EMAILS_STRING_SEPARATOR);
        }
    }

    @Override
    public User findByEmail(String email) {
        User user = findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        return user;
    }

    @Override
    public List<User> findAll() {
        return FileUtil.getFileFirstLineAsListOfStrings(fileLocation, EMAILS_STRING_SEPARATOR).stream().map(User::new).collect(Collectors.toList());
    }
}
