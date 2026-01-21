package com.example.retail_system_api.controller;

import com.example.retail_system_api.dto.request.ProductRequestDto;
import com.example.retail_system_api.dto.response.ProductResponseDto;
import com.example.retail_system_api.service.impl.ProductServiceImpl;
import com.example.retail_system_api.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> addProduct(
           @Valid @ModelAttribute ProductRequestDto dto,
           @Valid @RequestParam ("image")MultipartFile file
    )throws IOException {
        return productService.addProducts(dto,file);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> showAllProduct(){
        return productService.showAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> findProductById(
            @Valid @PathVariable Long id
    ){
        return productService.findProductById(id);
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> findProductByName(
           @Valid @PathVariable String productName
    ){
        return productService.findProductByName(productName);
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> findProductByCategoryName(
            @Valid @PathVariable String name
    ){
        return productService.findProductByCategoryName(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProducts(
            @Valid @ModelAttribute ProductRequestDto dto,
            @Valid @RequestParam("image") MultipartFile file,
            @Valid @PathVariable Long id
    )throws IOException{
        return productService.updateProductById(dto, file, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> removeProductById(
            @Valid @PathVariable Long id
    ){
        return productService.removeProductById(id);
    }
}
