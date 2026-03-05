package com.nusiss.molymarket_notification_service.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    private Long id;
    private Long senderId;
    private String message;
    private String type;
    private Date createdAt;
    private Boolean isRead;
    private Long reciepientId;
}
