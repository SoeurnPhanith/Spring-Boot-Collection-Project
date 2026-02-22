package com.example.retail_system_api.mapper;

import com.example.retail_system_api.dto.request.SaleRequestDto;
import com.example.retail_system_api.dto.response.SaleResponseDto;
import com.example.retail_system_api.entity.CustomersEntity;
import com.example.retail_system_api.entity.OrdersEntity;
import com.example.retail_system_api.entity.SaleEntity;
import org.springframework.stereotype.Component;

@Component
public interface SaleMapper {
    // DTO ➜ Entity (for save)
    SaleEntity dtoToEntity(
            SaleRequestDto dto,
            OrdersEntity order,
            CustomersEntity customer
    );

    // Entity ➜ DTO (for response)
    SaleResponseDto entityToDto(SaleEntity sale);
}
