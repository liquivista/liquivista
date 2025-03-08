package com.service.management.notification.service;

import com.service.management.notification.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    String createNotification(NotificationDto notificationDto);

    NotificationDto getNotificationById(Long id);

    List<NotificationDto> getNotificationsByRecipient(String recipient);

    List<NotificationDto> getAllNotifications();
}
