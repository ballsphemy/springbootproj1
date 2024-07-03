package com.aclc.InventoryManagementSystem.mapper;

import com.aclc.InventoryManagementSystem.dto.OrdersDto;
import com.aclc.InventoryManagementSystem.model.Orders;
import com.aclc.InventoryManagementSystem.model.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdersMapper {

    private final OrderItemsMapper orderItemsMapper;

    @Autowired
    public OrdersMapper(OrderItemsMapper orderItemsMapper) {
        this.orderItemsMapper = orderItemsMapper;
    }

    public OrdersDto toDto(Orders order) {
        if (order == null) {
            return null;
        }

        OrdersDto dto = new OrdersDto();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId()); // Set customer ID
        dto.setItems(order.getItems().stream().map(orderItemsMapper::toDto).collect(Collectors.toList()));
        dto.setAddress(order.getAddress());
        dto.setStatus(order.getStatus());
        dto.setLastUpdated(order.getLastUpdated());
        dto.setTotalPrice(order.getTotalPrice());
        return dto;
    }

    public Orders toEntity(OrdersDto dto) {
        if (dto == null) {
            return null;
        }

        Orders order = new Orders();
        order.setAddress(dto.getAddress());
        order.setStatus(dto.getStatus());
        order.setLastUpdated(dto.getLastUpdated());
        order.setTotalPrice(dto.getTotalPrice());
        // Don't set items here; it will be set separately
        return order;
    }

    public Orders toEntityWithItems(OrdersDto dto, List<OrderItems> items) {
        Orders order = toEntity(dto);
        if (order != null) {
            order.setItems(items);
        }
        return order;
    }
}