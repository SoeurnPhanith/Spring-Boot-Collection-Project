package com.example.retail_system_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_sale_items")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    @JsonBackReference
    private SaleEntity sale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductsEntity product;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal subTotal;
}
