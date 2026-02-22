package com.example.retail_system_api.service.impl;

import com.example.retail_system_api.dto.request.SaleRequestDto;
import com.example.retail_system_api.dto.response.SaleResponseDto;
import com.example.retail_system_api.entity.*;
import com.example.retail_system_api.enums.OrdersStatus;
import com.example.retail_system_api.exception.ResourceNotFoundException;
import com.example.retail_system_api.mapper.impl.SaleMapperImpl;
import com.example.retail_system_api.repo.CustomerRepository;
import com.example.retail_system_api.repo.OrdersRepository;
import com.example.retail_system_api.repo.SaleRepository;
import com.example.retail_system_api.repo.StockRepository;
import com.example.retail_system_api.service.SaleService;
import com.example.retail_system_api.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SaleMapperImpl saleMapper;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<SaleResponseDto>> createSale(SaleRequestDto dto){
        //1.find orderById
        OrdersEntity order = ordersRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (order.getStatus() != OrdersStatus.CONFIRMED) {
            throw new ResourceNotFoundException("Order must be CONFIRMED before payment");
        }

        //2.deduct stock with check payment method
        order.getItems().forEach(item -> {
            ProductsEntity product = item.getProduct();
            int remain = product.getStock().getStockQty() - item.getQuantity();
            if(remain < 0)
                throw new RuntimeException("Not enough stock for " + product.getProductName());

            product.getStock().setStockQty(remain);
            stockRepository.save(product.getStock());
        });

        //3. create Sale entity (payment success)
        SaleEntity sale = saleMapper.dtoToEntity(dto, order, order.getCustomers());

        //4. save entity
        SaleEntity savedSale = saleRepository.save(sale);

        //5. update order status → COMPLETED
        order.setStatus(OrdersStatus.COMPLETED);
        ordersRepository.save(order);

        //create invoice
        InvoiceEntity invoice = invoiceService.createInvoice(savedSale);

        //7. response
        SaleResponseDto response = saleMapper.entityToDto(savedSale);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Payment success, sale created", response));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<SaleResponseDto>>> getAllSales() {
        List<SaleEntity> sale = saleRepository.findAll();

        if (sale.isEmpty()) {
            throw new ResourceNotFoundException("No sale record");
        }

        List<SaleResponseDto> dtoList = sale.stream()
                .map(s -> saleMapper.entityToDto(s))
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>(200, "success", dtoList)
        );
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<SaleResponseDto>> getSaleById(Long id) {
        //1.get data from entity by id
        SaleEntity sale = saleRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("sale not found"));

        //2. map data from entity to dto
        SaleResponseDto dto = saleMapper.entityToDto(sale);
        return ResponseEntity.ok().body(new ApiResponse<>(
           200, "success",dto
        ));
    }




}
