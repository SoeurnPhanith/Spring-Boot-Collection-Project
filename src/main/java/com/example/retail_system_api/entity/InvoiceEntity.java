package com.example.retail_system_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_invoice")
@Data
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // link to sale
    @OneToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private SaleEntity sale;

    private String invoiceNumber; // unique invoice number

    private BigDecimal totalAmount;

    @CreationTimestamp
    private LocalDateTime issuedAt;

    private String notes; // optional
}

