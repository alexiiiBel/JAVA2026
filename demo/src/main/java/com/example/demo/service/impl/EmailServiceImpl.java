package com.example.demo.service.impl;

import com.example.demo.exception.ServiceException;
import com.example.demo.service.EmailService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);

    private static final String SMTP_HOST     = "smtp.gmail.com";
    private static final String SMTP_PORT     = "587";
    private static final String SENDER_EMAIL  = "your-app-email@gmail.com";   // TODO: externalize to properties
    private static final String SENDER_PASSWORD = "your-app-password";        // TODO: externalize to properties
    private static final String APP_BASE_URL  = "http://localhost:8080/demo"; // TODO: externalize to properties
    private static final String CONFIRM_PATH  = "/controller?command=confirm_registration&token=";
    private static final String SUBJECT       = "Подтверждение регистрации";

    private static EmailServiceImpl instance;

    private EmailServiceImpl() {}

    public static EmailServiceImpl getInstance() {
        if (instance == null) {
            synchronized (EmailServiceImpl.class) {
                if (instance == null) {
                    instance = new EmailServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void sendConfirmationEmail(String toEmail, String confirmationToken) throws ServiceException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(SUBJECT);

            String confirmUrl = APP_BASE_URL + CONFIRM_PATH + confirmationToken;
            String body = buildEmailBody(confirmUrl);
            message.setContent(body, "text/html; charset=UTF-8");

            Transport.send(message);
            logger.info("Confirmation email sent to {}", toEmail);
        } catch (MessagingException e) {
            logger.error("Failed to send confirmation email to {}", toEmail, e);
            throw new ServiceException("Failed to send confirmation email", e);
        }
    }

    private String buildEmailBody(String confirmUrl) {
        return "<html><body>"
                + "<h2>Подтверждение регистрации</h2>"
                + "<p>Для завершения регистрации перейдите по ссылке:</p>"
                + "<a href=\"" + confirmUrl + "\">Подтвердить email</a>"
                + "<p>Ссылка действительна 24 часа.</p>"
                + "</body></html>";
    }
}
