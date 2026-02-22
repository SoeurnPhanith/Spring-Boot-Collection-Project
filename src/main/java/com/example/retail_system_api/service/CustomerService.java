package com.example.retail_system_api.service;

import com.example.retail_system_api.dto.request.CustomerRequestDto;
import com.example.retail_system_api.dto.response.CustomerResponseDto;
import com.example.retail_system_api.entity.CustomersEntity;
import com.example.retail_system_api.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService  {
    //

    ResponseEntity<ApiResponse<CustomerResponseDto>> register(CustomerRequestDto reqDto);

    ResponseEntity<ApiResponse<List<CustomerResponseDto>>> showAllCustomer();

    ResponseEntity<ApiResponse<CustomerResponseDto>> findCustomerById(Long id);

    ResponseEntity<ApiResponse<CustomerResponseDto>> updateCustomer(CustomerRequestDto reqDto, Long id);

    ResponseEntity<ApiResponse<?>> removeCustomerById(Long id);
}
