package com.aclc.InventoryManagementSystem.controller;

import com.aclc.InventoryManagementSystem.dto.OrdersDto;
import com.aclc.InventoryManagementSystem.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    // Endpoint for creating a new order
    @PostMapping("/create")
    public ResponseEntity<OrdersDto> createOrder(@RequestBody OrdersDto ordersDto) {
        OrdersDto createdOrder = ordersService.createOrder(ordersDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // Endpoint for getting an order by ID
    @GetMapping("/all")
    public ResponseEntity<List<OrdersDto>> getAllOrders() {
        List<OrdersDto> orders = ordersService.getAllOrders();
        if (orders != null) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<OrdersDto> updateOrder(@PathVariable int id, @RequestBody OrdersDto ordersDto) {
        OrdersDto updatedOrder = ordersService.updateOrder(id, ordersDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdersDto> getOrderById(@PathVariable int id) {
        OrdersDto order = ordersService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrdersDto>> getOrdersByStatus(@PathVariable String status) {
        List<OrdersDto> orders = ordersService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        ordersService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/pending")
    public List<OrdersDto> getPendingOrders() {
        return ordersService.getOrdersByStatus("pending");
    }

    @GetMapping("/status/en-route")
    public List<OrdersDto> getEnrouteORders() {
        return ordersService.getOrdersByStatus("en-route");
    }
    // Other endpoints for updating, deleting, or getting orders...
}