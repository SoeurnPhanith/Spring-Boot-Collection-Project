package com.example.retail_system_api.controller;

import com.example.retail_system_api.dto.request.CustomerRequestDto;
import com.example.retail_system_api.dto.response.CustomerResponseDto;
import com.example.retail_system_api.service.impl.CustomerServiceImpl;
import com.example.retail_system_api.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> register(
            @Valid @RequestBody CustomerRequestDto dto
    ){
        return service.register(dto);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> getAllCustomers(){
        return service.showAllCustomer();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> findCustomersById(
            @Valid @PathVariable Long id
    ){
        return service.findCustomerById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> updateCustomers(
            @Valid @RequestBody CustomerRequestDto dto,
            @Valid @PathVariable Long id
    ){
        return service.updateCustomer(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> removeCustomers(
            @Valid @PathVariable Long id
    ){
        return service.removeCustomerById(id);
    }

}

