package com.aclc.InventoryManagementSystem.repository;

import com.aclc.InventoryManagementSystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
