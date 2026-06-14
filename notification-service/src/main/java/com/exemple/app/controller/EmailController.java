package com.exemple.app.controller;

import dto.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {
    private final JavaMailSender mailSender;

    @PostMapping
    public void send(
            @RequestBody EmailRequest request) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(request.email());
        message.setSubject(request.subject());
        message.setText(request.text());

        mailSender.send(message);
    }
}
