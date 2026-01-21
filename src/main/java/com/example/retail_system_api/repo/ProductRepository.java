package com.example.retail_system_api.repo;

import com.example.retail_system_api.entity.ProductsEntity;
import com.example.retail_system_api.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity, Long> {

    boolean existsByProductName(String productName);

    Optional<ProductsEntity> findByProductName(String name);

    List<ProductsEntity> findByCategory_CategoryName(String categoryName);

    List<ProductsEntity> findAllByStatus(Status status);
    //or raw queury
    @Query(
            value = "SELECT * FROM tb_products WHERE status = 'ACTIVE'",
            nativeQuery = true
    )
    List<ProductsEntity> findAllProductWithStatusACTIVE();
}
