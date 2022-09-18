package com.example.bitcoingenesis.service.email;

import com.example.bitcoingenesis.model.CryptoPriceInfo;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public interface EmailService {

    boolean sendEmail(SimpleMailMessage message);

    boolean sendEmailToAll(SimpleMailMessage message, List<String> emails);
}
