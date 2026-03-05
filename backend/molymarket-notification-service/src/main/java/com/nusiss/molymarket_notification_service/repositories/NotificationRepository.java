package com.nusiss.molymarket_notification_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nusiss.molymarket_notification_service.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReciepientId(Long reciepientId);
}
