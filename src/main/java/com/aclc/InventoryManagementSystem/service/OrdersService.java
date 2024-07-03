package com.aclc.InventoryManagementSystem.service;

import com.aclc.InventoryManagementSystem.dto.OrdersDto;

import java.util.List;

public interface OrdersService {
    public OrdersDto createOrder(OrdersDto ordersDto);
    public List<OrdersDto> getAllOrders();
    public OrdersDto getOrderById(int orderId);
    OrdersDto updateOrder(int orderId, OrdersDto ordersDto);
    public List<OrdersDto> getOrdersByStatus(String status);
    public void deleteOrder(int id);
}
