package com.nusiss.molymarket_product_order_service.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.nusiss.molymarket_product_order_service.dtos.OrderItemDto;
import com.nusiss.molymarket_product_order_service.entities.OrderItem;

public class OrderItemMapper {

    public static OrderItemDto toDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        OrderItemDto dto = new OrderItemDto();
        dto.setId(orderItem.getId());
        dto.setOrderId(orderItem.getOrder().getId());
        dto.setProductId(orderItem.getProduct().getId());
        dto.setSellerId(orderItem.getSellerId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPrice());
        dto.setStatus(orderItem.getStatus());
        return dto;
    }

    public static OrderItem toEntity(OrderItemDto dto) {
        if (dto == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setId(dto.getId());
        // orderItem.setOrder(order);
        // orderItem.setProduct(product);
        // orderItem.setSeller(seller);
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPrice(dto.getPrice());
        orderItem.setStatus(dto.getStatus());
        return orderItem;
    }

    public static List<OrderItemDto> toDTOList(List<OrderItem> orderItems) {
        return orderItems.stream().map(OrderItemMapper::toDTO).collect(Collectors.toList());
    }

    public static List<OrderItem> toEntityList(List<OrderItemDto> dtos) {
        return dtos.stream().map(dto -> toEntity(dto)).collect(Collectors.toList());
    }
}
