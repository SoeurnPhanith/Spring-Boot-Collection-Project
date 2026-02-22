package com.example.retail_system_api.service.impl;

import com.example.retail_system_api.dto.request.CategoryRequestDto;
import com.example.retail_system_api.dto.response.CategoryResponseDto;
import com.example.retail_system_api.entity.CategoryEntity;
import com.example.retail_system_api.exception.DuplicateResourceException;
import com.example.retail_system_api.exception.ResourceNotFoundException;
import com.example.retail_system_api.mapper.impl.CategoryMapperImpl;
import com.example.retail_system_api.repo.CategoryRepository;
import com.example.retail_system_api.service.CategoryService;
import com.example.retail_system_api.utils.ApiResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private CategoryMapperImpl mapper;

    @Override
    @Transactional
    public @NonNull ResponseEntity<ApiResponse<CategoryResponseDto>> addCategory(CategoryRequestDto category) {
        //1.check exists category
        boolean exists = categoryRepo.existsByCategoryName(category.getCategoryName());
        if(exists){
            throw new DuplicateResourceException("Category name is already exists");
        }

        //2. map data from DTO -> Entity
        CategoryEntity entity = mapper.dtoToEntity(category);

        //3.Save entity to db
        CategoryEntity saved = categoryRepo.save(entity);

        //4. map data from entity to DTO
        CategoryResponseDto dto = mapper.entityToDto(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        201,
                        "create resource success",dto
                )
        );
    }

    @Override
    public @NonNull ResponseEntity<ApiResponse<List<CategoryResponseDto>>> allCategory() {
        //1. find all resource & check empty in db?
        List<CategoryEntity> find = categoryRepo.findAll();
        if(find.isEmpty()){
            throw new ResourceNotFoundException("no category record");
        }

        //2. map list entity -> DTO
        List<CategoryResponseDto> dtoList = new ArrayList<>();
        for(CategoryEntity e : find){
            CategoryResponseDto dto = mapper.entityToDto(e);
            dtoList.add(dto);
        }
        return ResponseEntity.ok().body(
                new ApiResponse(200, "success", dtoList)
        );
    }

    @Override
    public @NonNull ResponseEntity<ApiResponse<CategoryResponseDto>> findCategoryById(Long id) {
        //1. find by id from db & check it found or not
        Optional<CategoryEntity> findById = categoryRepo.findById(id);
        if(!(findById.isPresent())){
            throw new ResourceNotFoundException("This category id not found");
        }

        //2.get data found
        CategoryEntity found = findById.get();

        //3. map from Entity -> DTO
        CategoryResponseDto dto = mapper.entityToDto(found);
        return ResponseEntity.ok().body(
                new ApiResponse<>(200, "success", dto)
        );
    }

    @Override
    public @NonNull ResponseEntity<ApiResponse<CategoryResponseDto>> findCategoryByName(String name) {
        //1. find by name in db & check it found or not
        Optional<CategoryEntity> findByName = categoryRepo.findByCategoryName(name);
        if(!findByName.isPresent()){
            throw new ResourceNotFoundException("This Category name not found!");
        }

        //2. get data found by name
        CategoryEntity found = findByName.get();

        //3. map data from Entity -> DTO
        CategoryResponseDto dto = mapper.entityToDto(found);
        return ResponseEntity.ok().body(
                new ApiResponse<>(200, "sucess", dto)
        );
    }

    @Override
    public @NonNull ResponseEntity<ApiResponse<CategoryResponseDto>> updateCategory(CategoryRequestDto category, Long id) {
        //1. find by id from db & check it found or not
        CategoryEntity findToUpdate = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This category id not found"));

        //2.take data found to update
        findToUpdate.setCategoryName(category.getCategoryName());

        //4. save data update on db
        CategoryEntity saved = categoryRepo.save(findToUpdate);

        //5. map data from Entity -> DTO
        CategoryResponseDto dto = mapper.entityToDto(saved);
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"update category success",dto)
        );
    }

    @Override
    public @NonNull ResponseEntity<ApiResponse<?>> removeCategory(Long id) {
        //1. find by id from db & check it found or not
        CategoryEntity removeById = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This category id not found"));

        //2.remove resource
        categoryRepo.delete(removeById);
        return ResponseEntity.ok().body(
                new ApiResponse<>(200, "remove success", removeById )
        );
    }
}
