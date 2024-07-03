package com.aclc.InventoryManagementSystem.controller;

import com.aclc.InventoryManagementSystem.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
