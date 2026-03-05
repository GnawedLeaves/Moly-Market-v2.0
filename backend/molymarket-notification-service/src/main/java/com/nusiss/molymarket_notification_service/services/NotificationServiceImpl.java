package com.nusiss.molymarket_notification_service.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.nusiss.molymarket_notification_service.dtos.NotificationDto;
import com.nusiss.molymarket_notification_service.entities.Notification;
import com.nusiss.molymarket_notification_service.mappers.NotificationMapper;
import com.nusiss.molymarket_notification_service.repositories.NotificationRepository;
import com.nusiss.molymarket_notification_service.services.interfaces.NotificationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public String createNotification(NotificationDto notificationDto) {

        Notification newNotification = NotificationMapper.toEntity(notificationDto);
        
        notificationRepository.save(newNotification);

        return "New notification created!";
    }

    @Override
    public String updateNotification(NotificationDto notificationDto) {

        Notification newNotification = NotificationMapper.toEntity(notificationDto);

        notificationRepository.findById(notificationDto.getId())
                .orElseThrow(() -> new RuntimeException("Notification not found"));
                
        notificationRepository.save(newNotification);

        return "Notification updated!";
    }

    @Override
    public List<NotificationDto> getNotificationsById(Long reciepientId) {
        List<Notification> notifications = notificationRepository.findByReciepientId(reciepientId);

        return NotificationMapper.toDtoList(notifications);
    }
}

