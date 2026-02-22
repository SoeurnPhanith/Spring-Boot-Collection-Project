package com.example.retail_system_api.entity;

import com.example.retail_system_api.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "tb_stock")
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_quantity")
    private Integer stockQty;

    //One Stock having ---->>> One Products
    @OneToOne(mappedBy = "stock")
    private ProductsEntity products;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
