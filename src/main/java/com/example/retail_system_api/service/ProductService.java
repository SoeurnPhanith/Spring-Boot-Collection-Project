package com.example.retail_system_api.service;

import com.example.retail_system_api.dto.request.ProductRequestDto;
import com.example.retail_system_api.dto.response.ProductResponseDto;
import com.example.retail_system_api.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {

    //todo : manage of products
    ResponseEntity<ApiResponse<ProductResponseDto>> addProducts
    (ProductRequestDto dto, MultipartFile file) throws IOException;

    ResponseEntity<ApiResponse<List<ProductResponseDto>>> showAllProducts();

    ResponseEntity<ApiResponse<ProductResponseDto>> findProductById(Long id);

    ResponseEntity<ApiResponse<ProductResponseDto>> findProductByName(String name);

    ResponseEntity<ApiResponse<List<ProductResponseDto>>> findProductByCategoryName(String categoryName);

    ResponseEntity<ApiResponse<ProductResponseDto>> updateProductById(ProductRequestDto dto, MultipartFile file, Long id) throws IOException;

    ResponseEntity<ApiResponse<?>> removeProductById(Long id);

}
