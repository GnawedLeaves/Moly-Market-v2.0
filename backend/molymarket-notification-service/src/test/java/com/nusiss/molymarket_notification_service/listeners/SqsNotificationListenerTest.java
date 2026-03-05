package com.nusiss.molymarket_notification_service.listeners;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nusiss.molymarket_notification_service.dtos.NotificationDto;
import com.nusiss.molymarket_notification_service.services.interfaces.NotificationService;

@ExtendWith(MockitoExtension.class)
public class SqsNotificationListenerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private SqsNotificationListener sqsNotificationListener;

    private NotificationDto notificationDto;

    @BeforeEach
    void setUp() {
        notificationDto = new NotificationDto();
        notificationDto.setMessage("Test message");
        notificationDto.setReciepientId(1L);
    }

    @Test
    void testListenSuccess() {
        sqsNotificationListener.listen(notificationDto);
        verify(notificationService).createNotification(notificationDto);
    }

    @Test
    void testListenFailure() {
        doThrow(new RuntimeException("Service error")).when(notificationService).createNotification(notificationDto);
        sqsNotificationListener.listen(notificationDto);
        verify(notificationService).createNotification(notificationDto);
    }
}
