package com.example.retail_system_api.repo;

import com.example.retail_system_api.entity.CustomersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomersEntity,Long> {

    boolean existsByUser_Email(String email);


}
