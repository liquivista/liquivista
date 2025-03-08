package com.service.management.notification.dto;

public record NotificationRequestDto(String recipient,String subject, String message, String notificationType,
                               String sender) {
}