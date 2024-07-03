package com.aclc.InventoryManagementSystem.controller;

import com.aclc.InventoryManagementSystem.dto.ProductDto;
import com.aclc.InventoryManagementSystem.service.ProductService;
import com.aclc.InventoryManagementSystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/image/";

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable int id) {
        ProductDto product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDto> saveProduct(@RequestPart("product") String productString,
                                                  @RequestPart("image") MultipartFile image) {
        try {
            // Convert the JSON string to ProductDto
            ObjectMapper objectMapper = new ObjectMapper();
            ProductDto productDto = objectMapper.readValue(productString, ProductDto.class);

            // Save the image file to the static folder
            String imageFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path imagePath = Paths.get(IMAGE_DIRECTORY, imageFileName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, image.getBytes());

            // Set the image path in the product DTO
            productDto.setImage(imageFileName);

            // Save the product
            ProductDto savedProduct = productService.saveProduct(productDto);
            return ResponseEntity.ok(savedProduct);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable int id, @ModelAttribute ProductDto productDto, @RequestParam(value = "image", required = false) MultipartFile image) {
        ProductDto existingProductDto = productService.getProductById(id);

        if (existingProductDto != null) {
            // Update product information
            existingProductDto.setName(productDto.getName());
            existingProductDto.setQuantity(productDto.getQuantity());
            existingProductDto.setPrice(productDto.getPrice());
            existingProductDto.setBuyPrice(productDto.getBuyPrice());

            System.out.println(existingProductDto);

            // Check if a new image is provided
            if (image != null && !image.isEmpty()) {
                try {
                    // Save the new image
                    String imageFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                    Path imagePath = Paths.get(IMAGE_DIRECTORY, imageFileName);
                    Files.createDirectories(imagePath.getParent());
                    Files.write(imagePath, image.getBytes());

                    // Update the product's image path
                    existingProductDto.setImage(imageFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }

            // Save the updated product
            ProductDto updatedProduct = productService.updateProduct(existingProductDto);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/image/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        try {
            Path imagePath = Paths.get(IMAGE_DIRECTORY, imageName);
            byte[] imageBytes = Files.readAllBytes(imagePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Adjust content type as per your image type

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/low")
    public List<ProductDto> getLowStockProducts() {
        return productService.getAllProducts().stream()
                .filter(product -> product.getQuantity() < 5)
                .collect(Collectors.toList());
    }

}
