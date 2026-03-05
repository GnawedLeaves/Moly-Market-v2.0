package com.nusiss.molymarket_notification_service.listeners;

import com.nusiss.molymarket_notification_service.dtos.NotificationDto;
import com.nusiss.molymarket_notification_service.services.interfaces.NotificationService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SqsNotificationListener {

    private final NotificationService notificationService;

    @SqsListener("${notification.queue.name}")
    public void listen(NotificationDto notificationDto) {
        log.info("Received notification from SQS: {}", notificationDto.getMessage());
        try {
            notificationService.createNotification(notificationDto);
        } catch (Exception e) {
            log.error("Failed to process notification from SQS: {}", e.getMessage());
        }
    }
}
