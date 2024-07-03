package com.aclc.InventoryManagementSystem.service;

import com.aclc.InventoryManagementSystem.dto.OrdersDto;
import com.aclc.InventoryManagementSystem.mapper.OrderItemsMapper;
import com.aclc.InventoryManagementSystem.mapper.OrdersMapper;
import com.aclc.InventoryManagementSystem.model.Customer;
import com.aclc.InventoryManagementSystem.model.OrderItems;
import com.aclc.InventoryManagementSystem.model.Orders;
import com.aclc.InventoryManagementSystem.model.Product;
import com.aclc.InventoryManagementSystem.repository.CustomerRepository;
import com.aclc.InventoryManagementSystem.repository.OrderItemsRepository;
import com.aclc.InventoryManagementSystem.repository.OrdersRepository;
import com.aclc.InventoryManagementSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderItemsRepository orderItemsRepository;



    @Override
    public OrdersDto createOrder(OrdersDto ordersDto) {
        Customer customer = customerRepository.findById(ordersDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        double[] totalPrice = {0.0}; // Initialize total price

        // Create the order entity
        Orders order = ordersMapper.toEntity(ordersDto); // This maps only Orders without items
        order.setCustomer(customer); // Set the customer

        // Map OrderItems DTOs to entities and calculate total price
        List<OrderItems> items = ordersDto.getItems().stream()
                .map(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    // Check if there is enough stock
                    if (product.getQuantity() < itemDto.getQuantity()) {
                        throw new RuntimeException("Not enough stock for product: " + product.getName());
                    }

                    // Subtract the ordered quantity from the product's available quantity
                    product.setQuantity(product.getQuantity() - itemDto.getQuantity());
                    productRepository.save(product); // Save the updated product

                    double itemPrice = product.getPrice() * itemDto.getQuantity(); // Calculate item price
                    totalPrice[0] += itemPrice; // Add item price to total

                    // Map OrderItems DTO to entity
                    OrderItems orderItem = orderItemsMapper.toEntity(itemDto, product);
                    return orderItem;
                }).collect(Collectors.toList());

        // Set the total price for the order
        order.setTotalPrice(totalPrice[0]); // Set the calculated total price
        order = ordersRepository.save(order); // Save the order

        // Set the order reference for each OrderItems
        // Set the order reference for each OrderItems
        for (OrderItems orderItem : items) {
            orderItem.setOrders(order);
        }

        // Save the order items
        orderItemsRepository.saveAll(items);

        return ordersMapper.toDto(order);
    }

    @Override
    public List<OrdersDto> getAllOrders() {
        List<Orders> orders = ordersRepository.findAll();
        return orders.stream()
                .map(order -> {
                    OrdersDto orderDto = ordersMapper.toDto(order);
                    List<OrderItems> orderItems = orderItemsRepository.findByOrders(order);
                    orderDto.setItems(orderItems.stream().map(orderItemsMapper::toDto).collect(Collectors.toList()));
                    return orderDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public OrdersDto getOrderById(int orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItems> orderItems = orderItemsRepository.findByOrders(order);
        OrdersDto orderDto = ordersMapper.toDto(order);
        orderDto.setItems(orderItems.stream().map(orderItemsMapper::toDto).collect(Collectors.toList()));
        return orderDto;
    }

    @Override
    public OrdersDto updateOrder(int id, OrdersDto ordersDto) {
        Orders existingOrder = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Customer customer = customerRepository.findById(ordersDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        double[] totalPrice = {0.0};

        // Get existing order items
        List<OrderItems> existingItems = orderItemsRepository.findByOrders(existingOrder);

        // Restore the product quantities of the existing items
        for (OrderItems existingItem : existingItems) {
            Product product = existingItem.getProduct();
            product.setQuantity(product.getQuantity() + existingItem.getQuantity());
            productRepository.save(product);
        }

        // Delete existing order items
        orderItemsRepository.deleteAll(existingItems);

        // Update order details
        existingOrder.setCustomer(customer);
        existingOrder.setAddress(ordersDto.getAddress());
        existingOrder.setStatus(ordersDto.getStatus());
        existingOrder.setLastUpdated(LocalDateTime.now());

        // Map new OrderItems DTOs to entities and calculate total price
        List<OrderItems> newItems = ordersDto.getItems().stream()
                .map(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    if (product.getQuantity() < itemDto.getQuantity()) {
                        throw new RuntimeException("Not enough stock for product: " + product.getName());
                    }

                    product.setQuantity(product.getQuantity() - itemDto.getQuantity());
                    productRepository.save(product);

                    double itemPrice = product.getPrice() * itemDto.getQuantity();
                    totalPrice[0] += itemPrice;

                    OrderItems orderItem = orderItemsMapper.toEntity(itemDto, product);
                    return orderItem;
                }).collect(Collectors.toList());

        // Set the total price for the order
        existingOrder.setTotalPrice(totalPrice[0]);
        existingOrder = ordersRepository.save(existingOrder);

        // Set the order reference for each OrderItems
        for (OrderItems orderItem : newItems) {
            orderItem.setOrders(existingOrder);
        }

        // Save the new order items
        orderItemsRepository.saveAll(newItems);

        return ordersMapper.toDto(existingOrder);
    }

    public List<OrdersDto> getOrdersByStatus(String status) {
        List<Orders> orders = ordersRepository.findByStatus(status);
        return orders.stream()
                .map(order -> ordersMapper.toDto(order))
                .collect(Collectors.toList());
    }
    @Override
    public void deleteOrder(int id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Retrieve order items
        List<OrderItems> orderItems = orderItemsRepository.findByOrders(order);

        // Delete order items
        orderItemsRepository.deleteAll(orderItems);

        // Restore product quantities
        for (OrderItems orderItem : orderItems) {
            Product product = orderItem.getProduct();
            product.setQuantity(product.getQuantity() + orderItem.getQuantity());
            productRepository.save(product);
        }

        // Delete the order
        ordersRepository.delete(order);
    }

}
// Other service methods...

