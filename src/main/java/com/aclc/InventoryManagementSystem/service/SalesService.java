package com.aclc.InventoryManagementSystem.service;

import java.util.List;
import java.util.Map;

public interface SalesService {
    double getCurrentSales();
    double getMonthlySales();
    double getTotalSales();
    int calculateTotalProductsSold();
    Map<String, Double> calculateProfitPerProduct();
    List<Map<String, Object>> getTopSellingProducts(int limit);
    Map<String, Double> getSalesByStatus();
    List<Map<String, Object>> getSalesOverTime(int days);
    double calculateAverageOrderValue();
    double calculateInventoryValue();
    Map<String, Object> getDashboardMetrics();
}
