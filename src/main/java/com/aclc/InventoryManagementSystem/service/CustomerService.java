package com.aclc.InventoryManagementSystem.service;


import com.aclc.InventoryManagementSystem.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    public List<CustomerDto> getAllCustomers();
    public CustomerDto getCustomerById(int id);
    public CustomerDto saveCustomer(CustomerDto customerDto);
    public void deleteCustomer(int id);
    public CustomerDto updateCustomer(CustomerDto customerDto);
}
