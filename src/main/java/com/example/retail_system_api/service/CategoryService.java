package com.example.retail_system_api.service;

import com.example.retail_system_api.dto.request.CategoryRequestDto;
import com.example.retail_system_api.dto.response.CategoryResponseDto;
import com.example.retail_system_api.utils.ApiResponse;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    @NonNull
    ResponseEntity<ApiResponse<CategoryResponseDto>> addCategory(CategoryRequestDto category);

    @NonNull
    ResponseEntity<ApiResponse<List<CategoryResponseDto>>> allCategory();

    @NonNull
    ResponseEntity< ApiResponse<CategoryResponseDto>>findCategoryById(Long id);

    @NonNull
    ResponseEntity<ApiResponse<CategoryResponseDto>> findCategoryByName(String name);

    @NonNull
    ResponseEntity< ApiResponse<CategoryResponseDto>> updateCategory(CategoryRequestDto category, Long id);

    @NonNull
    ResponseEntity<ApiResponse<?>> removeCategory(Long id);

}
