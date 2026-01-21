package com.example.retail_system_api.mapper;

import com.example.retail_system_api.dto.request.CategoryRequestDto;
import com.example.retail_system_api.dto.response.CategoryResponseDto;
import com.example.retail_system_api.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public interface CategoryMapper {

    //dto to entity
    CategoryEntity dtoToEntity(CategoryRequestDto dto);

    //entity to dto
    CategoryResponseDto entityToDto(CategoryEntity entity);

}
