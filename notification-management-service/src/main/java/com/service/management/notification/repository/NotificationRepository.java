package com.service.management.notification.repository;

import com.service.management.notification.model.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {
    List<NotificationModel> findByRecipient(String recipient);
}
