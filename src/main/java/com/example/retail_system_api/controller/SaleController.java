package com.example.retail_system_api.controller;

import com.example.retail_system_api.dto.request.SaleRequestDto;
import com.example.retail_system_api.dto.response.SaleResponseDto;
import com.example.retail_system_api.service.impl.SaleServiceImpl;
import com.example.retail_system_api.utils.ApiResponse;
import com.example.retail_system_api.utils.BaseEndPoint;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseEndPoint.endPoint + "/sale")
public class SaleController {

    @Autowired
    private SaleServiceImpl saleService;

    @PostMapping
    public ResponseEntity<ApiResponse<SaleResponseDto>> createSale(
           @Valid @RequestBody  SaleRequestDto dto
    ){
        return saleService.createSale(dto);
    }

    @GetMapping
    public  ResponseEntity<ApiResponse<List<SaleResponseDto>>> getAll(){
        return saleService.getAllSales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SaleResponseDto>> getSaleById(
            @PathVariable Long id
    ){
        return saleService.getSaleById(id);
    }
}
