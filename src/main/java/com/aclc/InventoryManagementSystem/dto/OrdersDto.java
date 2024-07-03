package com.aclc.InventoryManagementSystem.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrdersDto {
    private int id;
    private int customerId;
    private List<OrderItemsDto> items;
    private String address;
    private String status;
    private LocalDateTime lastUpdated;
    private double totalPrice;

    // No-argument constructor
    public OrdersDto() {}

    // Parameterized constructor with all fields
    public OrdersDto(int id, int customerId, List<OrderItemsDto> items, String address, String status, LocalDateTime lastUpdated, double totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.address = address;
        this.status = status;
        this.lastUpdated = lastUpdated;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemsDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemsDto> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}