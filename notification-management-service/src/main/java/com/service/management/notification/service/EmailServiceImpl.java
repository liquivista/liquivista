package com.service.management.notification.service;

import com.service.management.notification.dto.NotificationRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String sendEmail(NotificationRequestDto notificationDto) {

        if(notificationDto.recipient() == null || notificationDto.recipient().trim().isEmpty()){
            log.error("Recipient Address is Empty");
            return "Recipient Address is Empty";
        }

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(notificationDto.recipient());
        email.setSubject(notificationDto.subject());
        email.setText(notificationDto.message());

        try {
            javaMailSender.send(email);
            log.info("Email sent successfully to: {}", notificationDto.recipient());
            return "Email sent successfully to: "+ notificationDto.recipient();
        } catch (Exception e) {
            log.error("Failed to send email to: {}", notificationDto.recipient());
            return "Failed to send email to: "+ notificationDto.recipient();
        }
    }
}
