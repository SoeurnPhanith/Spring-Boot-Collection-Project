package com.example.retail_system_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_order_items")
public class OrdersItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many OrderItem -> One Order
    @ManyToOne
    @JoinColumn(name = "orders_id")
    private OrdersEntity orders;

    // One OrderItem -> One Product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductsEntity product;

    private BigDecimal price;

    private Integer quantity;
}
