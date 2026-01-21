package com.example.retail_system_api.controller;

import com.example.retail_system_api.dto.request.CategoryRequestDto;
import com.example.retail_system_api.dto.response.CategoryResponseDto;
import com.example.retail_system_api.service.impl.CategoryServiceImpl;
import com.example.retail_system_api.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping ("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl service;

    @PostMapping
    ResponseEntity<ApiResponse<CategoryResponseDto>> addCategory(
            @RequestBody @Valid CategoryRequestDto dto
    ){
        return service.addCategory(dto);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDto>>> getAll(){
        return service.allCategory();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> findById(
            @PathVariable @Valid Long id
    ){
        return service.findCategoryById(id);
    }

    @GetMapping("/name/{cate_name}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> findByName(
            @PathVariable @Valid String cate_name
    ){
        return service.findCategoryByName(cate_name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> updateCategory(
            @RequestBody @Valid CategoryRequestDto dto,
            @PathVariable @Valid Long id
    ){
        return service.updateCategory(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> removeById(
            @PathVariable @Valid Long id
    ){
        return service.removeCategory(id);
    }

}
