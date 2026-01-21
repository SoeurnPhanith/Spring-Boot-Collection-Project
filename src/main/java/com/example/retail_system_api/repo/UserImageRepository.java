package com.example.retail_system_api.repo;

import com.example.retail_system_api.entity.UsersImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends JpaRepository<UsersImageEntity , Long> {

    boolean existsByImageUrl(String image);

}
