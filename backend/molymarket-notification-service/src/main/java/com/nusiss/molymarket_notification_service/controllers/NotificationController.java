package com.nusiss.molymarket_notification_service.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nusiss.molymarket_notification_service.dtos.NotificationDto;
import com.nusiss.molymarket_notification_service.observers.NotificationManager;
import com.nusiss.molymarket_notification_service.services.NotificationServiceImpl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/notification/notif")
public class NotificationController {

    @Autowired
    private NotificationServiceImpl notificationServiceImpl;
    @Autowired
    private NotificationManager notificationManager;

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);


    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome, the notification endpoint is working!";
    }

    @PostMapping("/createNotification")
    public ResponseEntity<String> createNotification(@RequestBody NotificationDto notificationDto) {
        try {

            logger.info("CREATING NOTIFICATION: {}", notificationDto);

            String result = notificationServiceImpl.createNotification(notificationDto);

            notificationManager.notifyObservers(notificationDto);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @PostMapping("/updateNotification")
    public ResponseEntity<String> updateNotification(@RequestBody NotificationDto notificationDto) {
        try {
            return ResponseEntity.ok(notificationServiceImpl.updateNotification(notificationDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getNotifications")
    public ResponseEntity<List<NotificationDto>> getNotificationsById(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(notificationServiceImpl.getNotificationsById(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}
