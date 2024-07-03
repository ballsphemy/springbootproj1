package com.aclc.InventoryManagementSystem.mapper;

import com.aclc.InventoryManagementSystem.dto.OrderItemsDto;
import com.aclc.InventoryManagementSystem.model.OrderItems;
import com.aclc.InventoryManagementSystem.model.Product;
import org.springframework.stereotype.Component;

@Component
public class OrderItemsMapper {

    public OrderItemsDto toDto(OrderItems orderItems) {
        double totalPrice = orderItems.getProduct().getPrice() * orderItems.getQuantity();
        return new OrderItemsDto(
                orderItems.getId(),
                orderItems.getProduct().getId(),
                orderItems.getQuantity(),
                totalPrice
        );
    }
    public OrderItems toEntity(OrderItemsDto dto, Product product) {
        OrderItems orderItems = new OrderItems();
        orderItems.setId(dto.getId());
        orderItems.setProduct(product);
        orderItems.setQuantity(dto.getQuantity());
        return orderItems;
    }
}
