package com.example.retail_system_api.mapper.impl;

import com.example.retail_system_api.dto.request.CustomerRequestDto;
import com.example.retail_system_api.dto.response.CustomerResponseDto;
import com.example.retail_system_api.entity.CustomersEntity;
import com.example.retail_system_api.entity.UsersEntity;
import com.example.retail_system_api.mapper.CustomerMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomersMapperImpl implements CustomerMapper {


    @Override
    public CustomersEntity DtoToEntity(CustomerRequestDto dto) {
        CustomersEntity entity = new CustomersEntity();

        UsersEntity user = new UsersEntity();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        entity.setFirstName(dto.getFirstname());
        entity.setLastName(dto.getLastname());
        entity.setPhone(dto.getPhone());
        entity.setUser(user);
        entity.setDob(dto.getDob());
        entity.setAddress(dto.getAddress());

        return entity;
    }

    @Override
    public CustomerResponseDto entityToDto(CustomersEntity entity) {
        CustomerResponseDto dto = new CustomerResponseDto();

        dto.setFirstname(entity.getFirstName());
        dto.setLastname(entity.getLastName());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }



}