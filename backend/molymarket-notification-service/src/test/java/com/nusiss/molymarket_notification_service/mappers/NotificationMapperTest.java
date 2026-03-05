package com.nusiss.molymarket_notification_service.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.nusiss.molymarket_notification_service.dtos.NotificationDto;
import com.nusiss.molymarket_notification_service.entities.Notification;

public class NotificationMapperTest {

    @Test
    void testToDto() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setMessage("Test");
        
        NotificationDto dto = NotificationMapper.toDto(notification);
        
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Test", dto.getMessage());
    }

    @Test
    void testToDtoNull() {
        assertNull(NotificationMapper.toDto(null));
    }

    @Test
    void testToEntity() {
        NotificationDto dto = new NotificationDto();
        dto.setId(1L);
        dto.setMessage("Test");
        
        Notification notification = NotificationMapper.toEntity(dto);
        
        assertNotNull(notification);
        assertEquals(1L, notification.getId());
        assertEquals("Test", notification.getMessage());
    }

    @Test
    void testToEntityNull() {
        assertNull(NotificationMapper.toEntity(null));
    }

    @Test
    void testToDtoList() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setMessage("Test");
        
        List<NotificationDto> dtoList = NotificationMapper.toDtoList(Collections.singletonList(notification));
        
        assertNotNull(dtoList);
        assertEquals(1, dtoList.size());
        assertEquals(1L, dtoList.get(0).getId());
    }
}
