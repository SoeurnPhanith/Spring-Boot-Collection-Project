package com.example.retail_system_api.mapper.impl;

import com.example.retail_system_api.dto.request.CategoryRequestDto;
import com.example.retail_system_api.dto.response.CategoryResponseDto;
import com.example.retail_system_api.entity.CategoryEntity;
import com.example.retail_system_api.mapper.CategoryMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryEntity dtoToEntity(CategoryRequestDto dto) {
        CategoryEntity entity = new CategoryEntity();

        entity.setCategoryName(dto.getCategoryName());

        return entity;
    }

    @Override
    public CategoryResponseDto entityToDto(CategoryEntity entity) {
        CategoryResponseDto dto = new CategoryResponseDto();

        dto.setCategoryName(entity.getCategoryName());

        return dto;
    }
}
