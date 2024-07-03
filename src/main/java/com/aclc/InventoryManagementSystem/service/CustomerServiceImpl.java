package com.aclc.InventoryManagementSystem.service;

import com.aclc.InventoryManagementSystem.dto.CustomerDto;
import com.aclc.InventoryManagementSystem.mapper.CustomerMapper;
import com.aclc.InventoryManagementSystem.mapper.UserMapper;
import com.aclc.InventoryManagementSystem.model.Customer;
import com.aclc.InventoryManagementSystem.repository.CustomerRepository;
import com.aclc.InventoryManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(int id) {
        return customerRepository.findById(id).map(customerMapper::toDto).orElse(null);
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Override
    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerDto.getId());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            if (customerDto.getName() != null) {
                customer.setName(customerDto.getName());
            }
            if (customerDto.getAddress() != null) {
                customer.setAddress(customerDto.getAddress());
            }
            if (customerDto.getContactNumber() != null) {
                customer.setContactNumber(customerDto.getContactNumber());
            }

            customer = customerRepository.save(customer);
            return customerMapper.toDto(customer);
        } else {
            return null;
        }
    }
}
