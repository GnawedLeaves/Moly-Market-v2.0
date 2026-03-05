package com.nusiss.molymarket_notification_service.observers.interfaces;

import com.nusiss.molymarket_notification_service.dtos.NotificationDto;

public interface Observer {
    void receiveNotification(NotificationDto notification);
}