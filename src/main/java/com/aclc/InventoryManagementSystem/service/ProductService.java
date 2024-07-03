package com.aclc.InventoryManagementSystem.service;

import com.aclc.InventoryManagementSystem.dto.ProductDto;

import java.util.List;

public interface ProductService {
    public List<ProductDto> getAllProducts();
    public ProductDto getProductById(int id);
    public ProductDto saveProduct(ProductDto productDTO);
    public void deleteProduct(int id);
    public ProductDto updateProduct(ProductDto productDto);

}
