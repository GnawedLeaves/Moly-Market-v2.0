package com.nusiss.molymarket_notification_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nusiss.molymarket_notification_service.dtos.NotificationDto;
import com.nusiss.molymarket_notification_service.entities.Notification;
import com.nusiss.molymarket_notification_service.repositories.NotificationRepository;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private NotificationDto notificationDto;
    private Notification notification;

    @BeforeEach
    void setUp() {
        notificationDto = new NotificationDto();
        notificationDto.setId(1L);
        notificationDto.setMessage("Test Message");
        notificationDto.setReciepientId(1L);

        notification = new Notification();
        notification.setId(1L);
        notification.setMessage("Test Message");
        notification.setReciepientId(1L);
    }

    @Test
    void testCreateNotification() {
        String result = notificationService.createNotification(notificationDto);
        assertEquals("New notification created!", result);
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void testUpdateNotification() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        String result = notificationService.updateNotification(notificationDto);
        assertEquals("Notification updated!", result);
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void testUpdateNotificationNotFound() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> notificationService.updateNotification(notificationDto));
    }

    @Test
    void testGetNotificationsById() {
        when(notificationRepository.findByReciepientId(1L)).thenReturn(Collections.singletonList(notification));
        List<NotificationDto> result = notificationService.getNotificationsById(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Message", result.get(0).getMessage());
    }
}
