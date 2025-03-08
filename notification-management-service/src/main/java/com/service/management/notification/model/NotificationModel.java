package com.service.management.notification.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_service")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotificationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Column(name = "subject")
    private String subject;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "notification_type", nullable = false)
    private String notificationType;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "status")
    private String status;

    @Column(name = "sender", nullable = false)
    private String sender;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;
}
