package com.service.management.notification.service;

import com.service.management.notification.dto.NotificationDto;
import com.service.management.notification.model.NotificationModel;
import com.service.management.notification.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;

    private final EmailService emailService;

    @Override
    public String createNotification(NotificationDto notificationDto) {
        try {
            NotificationModel notificationModel = NotificationModel.builder()
                    .recipient(notificationDto.recipient())
                    .subject(notificationDto.subject())
                    .message(notificationDto.message())
                    .notificationType(notificationDto.notificationType())
                    .timestamp(LocalDateTime.now())
                    .status("active")
                    .sender(notificationDto.sender())
                    .isRead(false)
                    .build();

            NotificationModel response = notificationRepository.save(notificationModel);

            log.info("Notification Created Successfully With Notification Id: {}", response.getId());

            /*if(notificationModel.getNotificationType().equals("EMAIL")){
                String emailResponse = emailService.sendEmail(notificationDto);

                log.info("emailResponse+++++ :{}",emailResponse);
            }*/

            return "Notification Created Successfully With Notification Id: " + response.getId();
        } catch (Exception e) {
            log.error("Notification Not Created, Exception: {}", e.getMessage());
            return "Error creating notification: " + e.getMessage();
        }
    }

    @Override
    public NotificationDto getNotificationById(Long id) {
        try {
            NotificationModel notificationModel = notificationRepository.findById(id).orElse(null);
            if (notificationModel != null) {
                return new NotificationDto(
                        notificationModel.getId(),
                        notificationModel.getRecipient(),
                        notificationModel.getSubject(),
                        notificationModel.getMessage(),
                        notificationModel.getNotificationType(),
                        notificationModel.getTimestamp(),
                        notificationModel.getStatus(),
                        notificationModel.getSender(),
                        notificationModel.getIsRead()
                );
            } else {
                return null;
            }

        } catch (Exception e) {
            log.error("Error fetching notification by ID: {}, {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public List<NotificationDto> getNotificationsByRecipient(String recipient) {
        try {
            List<NotificationModel> notifications = notificationRepository.findByRecipient(recipient);
            log.info("getNotificationsByRecipient :{}-> {}",recipient, notifications);
            if (notifications != null && !notifications.isEmpty()) {
                return notifications.stream()
                        .map(notification -> new NotificationDto(
                                notification.getId(),
                                notification.getRecipient(),
                                notification.getSubject(),
                                notification.getMessage(),
                                notification.getNotificationType(),
                                notification.getTimestamp(),
                                notification.getStatus(),
                                notification.getSender(),
                                notification.getIsRead()
                        ))
                        .collect(Collectors.toList());
            } else {
                return List.of();
            }
        } catch (Exception e) {
            log.error("Error fetching notifications for recipient: {}, {}", recipient, e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<NotificationDto> getAllNotifications() {
        try {
            List<NotificationModel> notifications = notificationRepository.findAll();
            log.info("Get All Notifications :{}",notifications);
            if (!notifications.isEmpty()) {
                return notifications.stream()
                        .map(notification -> new NotificationDto(
                                notification.getId(),
                                notification.getRecipient(),
                                notification.getSubject(),
                                notification.getMessage(),
                                notification.getNotificationType(),
                                notification.getTimestamp(),
                                notification.getStatus(),
                                notification.getSender(),
                                notification.getIsRead()
                        ))
                        .collect(Collectors.toList());
            } else {
                return List.of();
            }
        } catch (Exception e) {
            log.error("Error fetching notifications for recipient: {}", e.getMessage());
            return List.of();
        }
    }

}
