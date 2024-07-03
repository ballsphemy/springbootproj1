package com.aclc.InventoryManagementSystem.service;


import com.aclc.InventoryManagementSystem.dto.ProductDto;
import com.aclc.InventoryManagementSystem.mapper.ProductMapper;
import com.aclc.InventoryManagementSystem.model.Product;
import com.aclc.InventoryManagementSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toList());
    }
    public ProductDto getProductById(int id) {
        return productRepository.findById(id).map(productMapper::toDto).orElse(null);
    }

    public ProductDto saveProduct(ProductDto productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public ProductDto updateProduct(ProductDto productDto) {
        Optional<Product> productOptional = productRepository.findById(productDto.getId());
        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();

            // Update fields only if they are not null in the DTO
            if (productDto.getName() != null) {
                existingProduct.setName(productDto.getName());
            }
            if (productDto.getQuantity() >= 0) {
                existingProduct.setQuantity(productDto.getQuantity());
            }
            if (productDto.getPrice() > 0) {
                existingProduct.setPrice(productDto.getPrice());
            }
            if (productDto.getBuyPrice() > 0) {
                existingProduct.setBuyPrice(productDto.getBuyPrice()); // Correctly update the buyPrice
            }

            if (productDto.getImage() != null) {
                existingProduct.setImage(productDto.getImage());
            }

            // Save the updated product
            existingProduct = productRepository.save(existingProduct);
            return productMapper.toDto(existingProduct);
        } else {
            return null; // or throw an exception if product with given id is not found
        }
    }
}
