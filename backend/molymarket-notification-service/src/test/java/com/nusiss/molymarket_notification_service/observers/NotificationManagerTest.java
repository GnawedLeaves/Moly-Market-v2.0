package com.nusiss.molymarket_notification_service.observers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nusiss.molymarket_notification_service.dtos.NotificationDto;
import com.nusiss.molymarket_notification_service.observers.interfaces.Observer;

public class NotificationManagerTest {

    private NotificationManager notificationManager;
    private Observer observer;
    private NotificationDto notificationDto;

    @BeforeEach
    void setUp() {
        notificationManager = new NotificationManager();
        observer = mock(Observer.class);
        notificationDto = new NotificationDto();
        notificationDto.setMessage("Test Message");
    }

    @Test
    void testAddAndNotifyObserver() {
        notificationManager.addObserver(observer);
        notificationManager.notifyObservers(notificationDto);
        verify(observer).receiveNotification(notificationDto);
    }

    @Test
    void testRemoveObserver() {
        notificationManager.addObserver(observer);
        notificationManager.removeObserver(observer);
        notificationManager.notifyObservers(notificationDto);
        // Should not be called because observer was removed
    }

    @Test
    void testClearObservers() {
        notificationManager.addObserver(observer);
        notificationManager.clearObservers();
        notificationManager.notifyObservers(notificationDto);
        // Should not be called because observers were cleared
    }
}
