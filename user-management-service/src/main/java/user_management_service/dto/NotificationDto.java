package user_management_service.dto;

import java.time.LocalDateTime;

public record NotificationDto(String recipient, String subject, String message, String notificationType,
                              String sender) {
}
