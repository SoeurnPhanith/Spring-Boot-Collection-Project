package com.example.retail_system_api.service.impl;

import com.example.retail_system_api.dto.request.ProductRequestDto;
import com.example.retail_system_api.dto.response.ProductResponseDto;
import com.example.retail_system_api.entity.CategoryEntity;
import com.example.retail_system_api.entity.ProductsEntity;
import com.example.retail_system_api.entity.StockEntity;
import com.example.retail_system_api.enums.Status;
import com.example.retail_system_api.exception.ResourceNotFoundException;
import com.example.retail_system_api.mapper.impl.ProductMapperImpl;
import com.example.retail_system_api.repo.CategoryRepository;
import com.example.retail_system_api.repo.ProductRepository;
import com.example.retail_system_api.repo.StockRepository;
import com.example.retail_system_api.service.ProductService;
import com.example.retail_system_api.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ProductMapperImpl productMapper;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private StockRepository stockRepo;

    @Autowired
    private Path uploadPath;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<ProductResponseDto>> addProducts(
            ProductRequestDto dto, MultipartFile file) throws IOException {

        //1. check exists products and add more to stock
        boolean existsProduct = productRepo.existsByProductName(dto.getProductName());
        if (existsProduct) {
            //find existing product
            ProductsEntity existingProduct = productRepo
                    .findByProductName(dto.getProductName())
                    .orElseThrow(() -> new ResourceNotFoundException("product not found"));

            //add more stock
            StockEntity stock = existingProduct.getStock();
            if (stock == null) {
                stock = new StockEntity();
                stock.setStockQty(dto.getStockQty());
                existingProduct.setStock(stock);
            } else {
                stock.setStockQty(stock.getStockQty() + dto.getStockQty());
            }
            stockRepo.save(stock);

            ProductResponseDto response = productMapper.entityToDto(existingProduct);

            return ResponseEntity.ok().body(
                    new ApiResponse<>(200, "stock updated successfully", response)
            );
        }

        //2. get categoryId
        CategoryEntity category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category not found"));

        //3. map data from dto to entity
        ProductsEntity products = productMapper.dtoToEntity(dto, category);

        //4. save stock to db
        StockEntity savedStock = products.getStock();
        stockRepo.save(savedStock);

        //5. upload image
        if (file != null) {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            file.transferTo(filePath);

            String httpPath = "http://localhost:8080";
            products.setProductImage(httpPath + "/images/" + filename);
        }

        //6. save product to db
        ProductsEntity saved = productRepo.save(products);

        //7. map data from entity to dto
        ProductResponseDto response = productMapper.entityToDto(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(201, "created new resource success", response)
        );
    }

    //show all data with status ACTIVE
    @Override
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> showAllProducts() {
        //1.get all data from db
        List<ProductsEntity> allProducts = productRepo.findAllProductWithStatusACTIVE();
        if(allProducts.isEmpty()){
            throw new ResourceNotFoundException("no products record");
        }

        //2.map data from entity -> DTO
        List<ProductResponseDto> dtoList = new ArrayList<>();
        for (ProductsEntity products : allProducts){
            ProductResponseDto dto = productMapper.entityToDto(products);

            dtoList.add(dto);
        }

        return ResponseEntity.ok().body(new ApiResponse<>(
           200, "success", dtoList
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<ProductResponseDto>> findProductById(Long id) {
        //1.find product by id
        ProductsEntity products = productRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("product not found"));

        //2.map data from entity dto
        ProductResponseDto dto = productMapper.entityToDto(products);

        return ResponseEntity.ok().body(new ApiResponse<>(
                200, "success", dto
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<ProductResponseDto>> findProductByName(String name) {
        //1.find product by name
        ProductsEntity products = productRepo.findByProductName(name)
                .orElseThrow(()->new ResourceNotFoundException("product not found"));

        //2.map data from entity to dto
        ProductResponseDto dto = productMapper.entityToDto(products);

        return ResponseEntity.ok().body(new ApiResponse<>(
           200, "success",dto
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> findProductByCategoryName(String categoryName) {
        //1.find product by category name
        List<ProductsEntity> products = productRepo.findByCategory_CategoryName(categoryName);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("category name not found");
        }

        //2.map data from entity to dto
        List<ProductResponseDto> dtoList = new ArrayList<>();
        for (ProductsEntity pro : products){
            ProductResponseDto dto = productMapper.entityToDto(pro);
            dtoList.add(dto);
        }

        return ResponseEntity.ok().body(new ApiResponse<>(
           200, "success", dtoList
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProductById(ProductRequestDto dto, MultipartFile file, Long id) throws IOException{
        //1.find product by id for update
        ProductsEntity products = productRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("product not found"));

        //2.get categoryId and stockId
        CategoryEntity category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("category not found"));

        //3. update entity found
        products.setProductName(dto.getProductName());
        products.setPrice(dto.getPrice());
        products.setDescription(dto.getDescription());
        products.setCategory(category);
        if(file!=null){
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

            //save file to folder
            Path filePath = uploadPath.resolve(filename);
            file.transferTo(filePath);

            String httpPath = "http://localhost:8080";
            dto.setImageUrl(httpPath + "/images/" + filename);
        }
        products.setProductImage(dto.getImageUrl());

        //4.save data update
        ProductsEntity saved = productRepo.save(products);

        //5.map data from entity to dto
        ProductResponseDto response = productMapper.entityToDto(saved);
        return ResponseEntity.ok().body(new ApiResponse<>(
           200, "update success", response
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<?>> removeProductById(Long id) {
        //1.find product by id for remove (change status)
        ProductsEntity products = productRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("product not found"));

        //update status
        products.setStatus(Status.INACTIVE);

        //save to db again
        productRepo.save(products);

        return ResponseEntity.ok().body(new ApiResponse<>(
                200, "success", "remove product success"
        ));

    }
}
