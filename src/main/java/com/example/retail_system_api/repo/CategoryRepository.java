package com.example.retail_system_api.repo;

import com.example.retail_system_api.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    boolean existsByCategoryName(String name);

    Optional<CategoryEntity> findByCategoryName(String name);
}
