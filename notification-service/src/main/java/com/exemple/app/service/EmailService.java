package com.exemple.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendCreated(String email) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Аккаунт создан");

        message.setText(
                "Здравствуйте! Ваш аккаунт на сайте был успешно создан."
        );

        mailSender.send(message);
    }

    public void sendDeleted(String email) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Аккаунт удалён");

        message.setText(
                "Здравствуйте! Ваш аккаунт был удалён."
        );

        mailSender.send(message);
    }
}
