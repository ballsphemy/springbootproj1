package com.aclc.InventoryManagementSystem.repository;

import com.aclc.InventoryManagementSystem.model.OrderItems;
import com.aclc.InventoryManagementSystem.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
    List<OrderItems> findByOrders(Orders orders);
}
