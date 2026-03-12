package com.nusiss.molymarket_product_order_service.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.nusiss.molymarket_product_order_service.dtos.NotificationDto;
import io.awspring.cloud.sqs.operations.SqsTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceClient {

    private final SqsTemplate sqsTemplate;

    @Value("${notification.queue.name}")
    private String queueName;

    public void createNotification(NotificationDto notification) {
        log.info("Sending notification to queue {}: {}", queueName, notification.getMessage());
        sqsTemplate.send(queueName, notification);
    }
}
