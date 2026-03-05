package com.nusiss.molymarket_notification_service.services.interfaces;

import java.util.List;

import com.nusiss.molymarket_notification_service.dtos.NotificationDto;

public interface NotificationService {
    
    public String createNotification(NotificationDto notificationDto);
    public String updateNotification(NotificationDto notificationDto);
    public List<NotificationDto> getNotificationsById(Long reciepientId);
}
