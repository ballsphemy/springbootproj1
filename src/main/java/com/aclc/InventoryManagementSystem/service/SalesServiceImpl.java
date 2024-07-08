package com.aclc.InventoryManagementSystem.service;

import com.aclc.InventoryManagementSystem.model.OrderItems;
import com.aclc.InventoryManagementSystem.model.Orders;
import com.aclc.InventoryManagementSystem.model.Product;
import com.aclc.InventoryManagementSystem.repository.OrderItemsRepository;
import com.aclc.InventoryManagementSystem.repository.OrdersRepository;
import com.aclc.InventoryManagementSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private ProductRepository productRepository;

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

    public int calculateTotalProductsSold() {
        return orderItemsRepository.findAll().stream()
                .mapToInt(OrderItems::getQuantity)
                .sum();
    }

    public Map<String, Double> calculateProfitPerProduct() {
        Map<String, Double> profitPerProduct = new HashMap<>();
        List<OrderItems> orderItems = orderItemsRepository.findAll();

        for (OrderItems item : orderItems) {
            Product product = item.getProduct();
            double profit = (product.getPrice() - product.getBuyPrice()) * item.getQuantity();
            profitPerProduct.merge(product.getName(), profit, Double::sum);
        }

        return profitPerProduct;
    }

    public List<Map<String, Object>> getTopSellingProducts(int limit) {
        return orderItemsRepository.findAll().stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getName(),
                        Collectors.summingInt(OrderItems::getQuantity)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("productName", entry.getKey());
                    result.put("quantity", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }

    public Map<String, Double> getSalesByStatus() {
        return ordersRepository.findAll().stream()
                .collect(Collectors.groupingBy(Orders::getStatus,
                        Collectors.summingDouble(Orders::getTotalPrice)));
    }

    public List<Map<String, Object>> getSalesOverTime(int days) {
        LocalDateTime startDate = LocalDate.now().minusDays(days).atStartOfDay();
        List<Orders> orders = ordersRepository.findByLastUpdatedAfter(startDate);

        return orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getLastUpdated().toLocalDate(),
                        Collectors.summingDouble(Orders::getTotalPrice)))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("date", entry.getKey());
                    result.put("sales", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }

    public double calculateAverageOrderValue() {
        List<Orders> orders = ordersRepository.findByStatus("delivered");
        return orders.stream().mapToDouble(Orders::getTotalPrice).average().orElse(0.0);
    }

    public double calculateInventoryValue() {
        return productRepository.findAll().stream()
                .mapToDouble(product -> product.getQuantity() * product.getBuyPrice())
                .sum();
    }

    public Map<String, Object> getDashboardMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("currentSales", getCurrentSales());
        metrics.put("monthlySales", getMonthlySales());
        metrics.put("totalSales", getTotalSales());
        metrics.put("totalProductsSold", calculateTotalProductsSold());
        metrics.put("topSellingProducts", getTopSellingProducts(5));
        metrics.put("salesByStatus", getSalesByStatus());
        metrics.put("salesOverTime", getSalesOverTime(30));
        metrics.put("averageOrderValue", calculateAverageOrderValue());
        metrics.put("inventoryValue", calculateInventoryValue());
        metrics.put("ProfitPerProduct", calculateProfitPerProduct());
        return metrics;
    }
}