package com.nusiss.molymarket_notification_service.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nusiss.molymarket_notification_service.dtos.NotificationDto;
import com.nusiss.molymarket_notification_service.observers.NotificationManager;
import com.nusiss.molymarket_notification_service.services.NotificationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {

    @Mock
    private NotificationServiceImpl notificationService;

    @Mock
    private NotificationManager notificationManager;

    @InjectMocks
    private NotificationController notificationController;

    private NotificationDto notificationDto;

    @BeforeEach
    void setUp() {
        notificationDto = new NotificationDto();
        notificationDto.setId(1L);
        notificationDto.setMessage("Test Message");
        notificationDto.setReciepientId(1L);
    }

    @Test
    void testWelcome() {
        String result = notificationController.welcome();
        assertEquals("Welcome, the notification endpoint is working!", result);
    }

    @Test
    void testCreateNotification() {
        when(notificationService.createNotification(any(NotificationDto.class))).thenReturn("New notification created!");
        
        ResponseEntity<String> response = notificationController.createNotification(notificationDto);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("New notification created!", response.getBody());
        verify(notificationManager).notifyObservers(notificationDto);
    }

    @Test
    void testUpdateNotification() {
        when(notificationService.updateNotification(any(NotificationDto.class))).thenReturn("Notification updated!");
        
        ResponseEntity<String> response = notificationController.updateNotification(notificationDto);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notification updated!", response.getBody());
    }

    @Test
    void testGetNotificationsById() {
        when(notificationService.getNotificationsById(1L)).thenReturn(Collections.singletonList(notificationDto));
        
        ResponseEntity<List<NotificationDto>> response = notificationController.getNotificationsById(1L);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
