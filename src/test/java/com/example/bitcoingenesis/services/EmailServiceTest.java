package com.example.bitcoingenesis.services;

import com.example.bitcoingenesis.service.EmailService;
import com.example.bitcoingenesis.service.EmailServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.List;

import static com.example.bitcoingenesis.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    private EmailService emailService;

    @Mock
    private JavaMailSender mailSender;

    @Before
    public void beforeTests() {
        this.emailService = new EmailServiceImpl(mailSender, SERVER_EMAIL);
    }

    @Test
    public void sendEmailSuccessful() {
        boolean result = emailService.sendEmail(SIMPLE_MAIL_MESSAGE);

        assertTrue(result);
    }

    @Test
    public void sendEmailFailed() {
        doThrow(RuntimeException.class).when(mailSender).send(SIMPLE_MAIL_MESSAGE);

        boolean result = emailService.sendEmail(SIMPLE_MAIL_MESSAGE);

        assertFalse(result);
    }

    @Test
    public void createMessageFromCryptocurrencyShortPriceInfo() {
        SimpleMailMessage simpleMailMessage = emailService.createMessageFromCryptocurrencyShortPriceInfo(SHORT_PRICE_INFO);

        assertEquals(getSimpleMailMessage().getFrom(), simpleMailMessage.getFrom());
        assertEquals(getSimpleMailMessage().getSubject(), simpleMailMessage.getSubject());
        assertEquals(getSimpleMailMessage().getText(), simpleMailMessage.getText());
    }
    @Test
    public void sendEmailToAll() {
        List<String> emails = List.of(EMAIL, SERVER_EMAIL);
        SimpleMailMessage mailMessage = getSimpleMailMessage();

        emailService.sendEmailToAll(mailMessage, emails);

        assertNotNull(mailMessage.getTo());
        verify(mailSender, times(emails.size())).send(mailMessage);
    }


    private SimpleMailMessage getSimpleMailMessage() {
        SimpleMailMessage message = SIMPLE_MAIL_MESSAGE;
        message.setSubject(getSubjectOfMailMessage());
        message.setText(getTextOfMailMessage());
        return message;
    }

    private String getSubjectOfMailMessage() {
        return CRYPTO + " rate";
    }

    private String getTextOfMailMessage() {
        return String.format("1 %s = %d %s (%s)", CRYPTO, PRICE, CURRENCY, CURRENCY.getName());
    }
}
