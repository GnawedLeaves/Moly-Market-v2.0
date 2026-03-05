package com.nusiss.molymarket_notification_service.observers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nusiss.molymarket_notification_service.dtos.NotificationDto;
import com.nusiss.molymarket_notification_service.observers.interfaces.Observer;


@Component
public class NotificationManager {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer user) {
        observers.add(user);
    }

    public void removeObserver(Observer user) {
        observers.remove(user);
    }

    public void notifyObservers(NotificationDto notification) {
        for (Observer observer : observers) {
            observer.receiveNotification(notification);
        }
    }

    public void clearObservers() {
        observers.clear();
    }
}