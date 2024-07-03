package com.aclc.InventoryManagementSystem.repository;

import com.aclc.InventoryManagementSystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
