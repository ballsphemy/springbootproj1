package com.aclc.InventoryManagementSystem.mapper;

import com.aclc.InventoryManagementSystem.dto.ProductDto;
import com.aclc.InventoryManagementSystem.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto toDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getQuantity(), product.getPrice(), product.getImage(), product.getBuyPrice());
    }

    public Product toEntity(ProductDto productDTO) {
        return new Product(productDTO.getName(), productDTO.getQuantity(), productDTO.getPrice(), productDTO.getImage(), productDTO.getBuyPrice());
    }
}