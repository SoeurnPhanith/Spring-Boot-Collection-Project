package com.example.retail_system_api.entity;

import com.example.retail_system_api.enums.OrdersStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "tb_orders")
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Many Order --->>> ordered by One Customer
    @ManyToOne
    @JoinColumn(name = "customers_id")
    private CustomersEntity customers;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private OrdersStatus status;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // One Order -> Many OrderItems
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrdersItemEntity> items;

}
