package com.service.management.notification.controller;

import com.service.management.notification.dto.NotificationDto;
import com.service.management.notification.dto.NotificationRequestDto;
import com.service.management.notification.service.EmailService;
import com.service.management.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification-management")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    private final EmailService emailService;

    @PostMapping("/create-notification")
    public ResponseEntity<?> createNotification(@RequestBody NotificationDto notificationDto){
        String response = notificationService.createNotification(notificationDto);
        if(response != null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error in Create Notification", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-notification/{id}")
    public ResponseEntity<?> getNotification(@PathVariable Long id) {
        NotificationDto notificationDto = notificationService.getNotificationById(id);
        if (notificationDto != null) {
            return new ResponseEntity<>(notificationDto, HttpStatus.OK);
        }
        return new ResponseEntity<>("Notification not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get-notification/recipient/{recipient}")
    public ResponseEntity<?> getNotificationByRecipient(@PathVariable String recipient) {
        List<NotificationDto> notifications = notificationService.getNotificationsByRecipient(recipient);
        if (notifications != null && !notifications.isEmpty()) {
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        }
        return new ResponseEntity<>("No notifications found for the recipient", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get-all-notifications")
    public ResponseEntity<?> getAllNotification() {
        List<NotificationDto> notifications = notificationService.getAllNotifications();
        if (notifications != null && !notifications.isEmpty()) {
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        }
        return new ResponseEntity<>("No notifications found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody NotificationRequestDto notificationRequestDto) {
        String emailSent = emailService.sendEmail(notificationRequestDto);
            return ResponseEntity.ok(emailSent);
    }

}
