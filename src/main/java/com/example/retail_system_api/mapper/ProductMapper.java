package com.example.retail_system_api.mapper;

import com.example.retail_system_api.dto.request.ProductRequestDto;
import com.example.retail_system_api.dto.response.ProductResponseDto;
import com.example.retail_system_api.entity.CategoryEntity;
import com.example.retail_system_api.entity.ProductsEntity;
import com.example.retail_system_api.entity.StockEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductMapper {

    //todo : map data from dto to entity
    ProductsEntity dtoToEntity(ProductRequestDto dto, CategoryEntity category);

    //todo : map data from entity to dto
    ProductResponseDto entityToDto(ProductsEntity entity);

}
