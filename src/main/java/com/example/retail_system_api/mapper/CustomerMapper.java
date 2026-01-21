package com.example.retail_system_api.mapper;

import com.example.retail_system_api.dto.request.CustomerRequestDto;
import com.example.retail_system_api.dto.request.UserRequestDto;
import com.example.retail_system_api.dto.response.CustomerResponseDto;
import com.example.retail_system_api.entity.CustomersEntity;
import com.example.retail_system_api.entity.UsersEntity;
import org.springframework.stereotype.Component;

@Component
public interface CustomerMapper {

    //Dto -->> CustomerEntity
    CustomersEntity DtoToEntity(CustomerRequestDto dto);

    //CustomerEntity --> Entity
    CustomerResponseDto entityToDto(CustomersEntity entity);

}
