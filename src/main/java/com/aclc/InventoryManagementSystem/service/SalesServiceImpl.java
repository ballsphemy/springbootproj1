package com.aclc.InventoryManagementSystem.service;

import com.aclc.InventoryManagementSystem.model.Orders;
import com.aclc.InventoryManagementSystem.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private OrdersRepository ordersRepository;

    public double getCurrentSales() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Orders> orders = ordersRepository.findByLastUpdatedBetweenAndStatus(startOfDay, endOfDay, "delivered");
        return orders.stream().mapToDouble(Orders::getTotalPrice).sum();
    }

    public double getMonthlySales() {
        LocalDateTime startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime endOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);

        List<Orders> orders = ordersRepository.findByLastUpdatedBetweenAndStatus(startOfMonth, endOfMonth, "delivered");
        return orders.stream().mapToDouble(Orders::getTotalPrice).sum();
    }

    public double getTotalSales() {
        List<Orders> orders = ordersRepository.findByStatus("delivered");
        return orders.stream().mapToDouble(Orders::getTotalPrice).sum();
    }
}