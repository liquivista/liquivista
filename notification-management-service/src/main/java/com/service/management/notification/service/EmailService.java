package com.service.management.notification.service;

import com.service.management.notification.dto.NotificationDto;
import com.service.management.notification.dto.NotificationRequestDto;

public interface EmailService {

    String sendEmail(NotificationRequestDto notificationRequestDto);
}
