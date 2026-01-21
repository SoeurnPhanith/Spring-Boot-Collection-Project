package com.example.retail_system_api.entity;

import com.example.retail_system_api.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_sales")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomersEntity customer;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // CASH, QR, CARD

    @CreationTimestamp
    private LocalDateTime saleDate;

    //One Sale -->> Many Sale Item
    @OneToMany(mappedBy = "sale")
    private List<SaleItemsEntity> salesItems;

}
