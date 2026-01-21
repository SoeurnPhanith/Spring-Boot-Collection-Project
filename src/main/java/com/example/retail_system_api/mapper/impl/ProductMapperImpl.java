package com.example.retail_system_api.mapper.impl;

import com.example.retail_system_api.dto.request.ProductRequestDto;
import com.example.retail_system_api.dto.response.ProductResponseDto;
import com.example.retail_system_api.entity.*;
import com.example.retail_system_api.enums.Status;
import com.example.retail_system_api.mapper.ProductMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements ProductMapper {

    //todo: DTO ---> Entity
    @Override
    public ProductsEntity dtoToEntity(ProductRequestDto dto, CategoryEntity category) {
        ProductsEntity entity = new ProductsEntity();

        StockEntity stock = new StockEntity();
        stock.setStockQty(dto.getStockQty());

        entity.setProductName(dto.getProductName());
        entity.setPrice(dto.getPrice());
        entity.setProductImage(dto.getImageUrl());
        entity.setCategory(category);
        entity.setDescription(dto.getDescription());
        entity.setStatus(Status.ACTIVE);
        entity.setStock(stock);

        return entity;
    }


    //todo: Entity ---> DTO
    @Override
    public ProductResponseDto entityToDto(ProductsEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.setProductName(entity.getProductName());
        dto.setPrice(entity.getPrice());
        dto.setImageUrl(entity.getProductImage());
        dto.setCategoryName(entity.getCategory().getCategoryName());
        dto.setStockQuantity(entity.getStock().getStockQty());
        dto.setStatus(entity.getStatus().name());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }
}
