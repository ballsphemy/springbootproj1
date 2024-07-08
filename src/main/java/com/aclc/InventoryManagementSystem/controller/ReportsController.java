package com.aclc.InventoryManagementSystem.controller;

import com.aclc.InventoryManagementSystem.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/reports")
public class ReportsController {
    @Autowired
    private SalesService salesService;

    @GetMapping("/current-date")
    public double getCurrentSales() {
        return salesService.getCurrentSales();
    }

    @GetMapping("/monthly")
    public double getMonthlySales() {
        return salesService.getMonthlySales();
    }

    @GetMapping("/total")
    public double getTotalSales() {
        return salesService.getTotalSales();
    }

    @GetMapping("/total-products-sold")
    public int getTotalProductsSold() {
        return salesService.calculateTotalProductsSold();
    }

    @GetMapping("/profit-per-product")
    public Map<String, Double> getProfitPerProduct() {
        return salesService.calculateProfitPerProduct();
    }

    @GetMapping("/top-selling-products")
    public List<Map<String, Object>> getTopSellingProducts(@RequestParam(defaultValue = "5") int limit) {
        return salesService.getTopSellingProducts(limit);
    }

    @GetMapping("/sales-by-status")
    public Map<String, Double> getSalesByStatus() {
        return salesService.getSalesByStatus();
    }

    @GetMapping("/sales-over-time")
    public List<Map<String, Object>> getSalesOverTime(@RequestParam(defaultValue = "30") int days) {
        return salesService.getSalesOverTime(days);
    }

    @GetMapping("/average-order-value")
    public double getAverageOrderValue() {
        return salesService.calculateAverageOrderValue();
    }

    @GetMapping("/inventory-value")
    public double getInventoryValue() {
        return salesService.calculateInventoryValue();
    }

    @GetMapping("/dashboard-metrics")
    public Map<String, Object> getDashboardMetrics() {
        return salesService.getDashboardMetrics();
    }
}