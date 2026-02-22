package com.example.retail_system_api.service;

import com.example.retail_system_api.dto.request.SaleRequestDto;
import com.example.retail_system_api.dto.response.SaleResponseDto;
import com.example.retail_system_api.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SaleService {

    ResponseEntity<ApiResponse<SaleResponseDto>> createSale(SaleRequestDto dto);

    ResponseEntity<ApiResponse<List<SaleResponseDto>>> getAllSales();

    ResponseEntity<ApiResponse<SaleResponseDto>> getSaleById(Long id);

}
