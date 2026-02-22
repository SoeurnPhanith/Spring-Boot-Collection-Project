package com.example.retail_system_api.entity;

import com.example.retail_system_api.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math .BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "tb_products")
public class ProductsEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String productName;

    private BigDecimal price;

    @Column(name = "images_url")
    private String productImage;

    //Many Product having --->>> One Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    //Many Product createBy -->>> One User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity createBy;

    //One Product --->> One Stock
    @OneToOne
    @JoinColumn(name = "stock_id")
    private StockEntity stock;

    //One Product -->> many orderItem
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrdersItemEntity> items;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
