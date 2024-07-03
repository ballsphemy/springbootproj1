package com.aclc.InventoryManagementSystem.mapper;

import com.aclc.InventoryManagementSystem.dto.CustomerDto;
import com.aclc.InventoryManagementSystem.dto.ProductDto;
import com.aclc.InventoryManagementSystem.model.Customer;
import com.aclc.InventoryManagementSystem.model.Product;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerDto toDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName(), customer.getAddress(), customer.getContactNumber());
    }

    public Customer toEntity(CustomerDto customerDto) {
        return new Customer(customerDto.getId(), customerDto.getName(), customerDto.getAddress(), customerDto.getContactNumber());
    }
}
