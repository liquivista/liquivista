package com.service.management.notification.dto;

import java.time.LocalDateTime;

public record NotificationDto(Long id, String recipient,String subject, String message, String notificationType,
                              LocalDateTime timestamp, String status, String sender, Boolean isRead  ) {
}
