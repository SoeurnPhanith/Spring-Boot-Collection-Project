package com.example.retail_system_api.repo;

import com.example.retail_system_api.entity.OrdersItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrdersItemEntity,Long> {
}
