package com.example.retail_system_api.mapper;

import com.example.retail_system_api.dto.request.OrdersRequestDTO;
import com.example.retail_system_api.dto.response.OrdersResponseDTO;
import com.example.retail_system_api.entity.CustomersEntity;
import com.example.retail_system_api.entity.OrdersEntity;
import com.example.retail_system_api.entity.ProductsEntity;
import org.springframework.stereotype.Component;

@Component
public interface OrdersMapper {

    //todo: order mapper dto to entity
    OrdersEntity orderDtoToEntity(OrdersRequestDTO dto, CustomersEntity customers);

    //todo: order mapper entity to dto
    OrdersResponseDTO orderEntityToDto(OrdersEntity entity);

}
