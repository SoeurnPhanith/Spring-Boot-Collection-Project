package com.example.retail_system_api.service.impl;

import com.example.retail_system_api.dto.request.CustomerRequestDto;
import com.example.retail_system_api.dto.response.CustomerResponseDto;
import com.example.retail_system_api.entity.CustomersEntity;
import com.example.retail_system_api.entity.RoleEntity;
import com.example.retail_system_api.entity.UsersEntity;
import com.example.retail_system_api.entity.UsersImageEntity;
import com.example.retail_system_api.enums.Status;
import com.example.retail_system_api.exception.DuplicateResourceException;
import com.example.retail_system_api.exception.ResourceNotFoundException;
import com.example.retail_system_api.mapper.impl.CustomersMapperImpl;
import com.example.retail_system_api.repo.CustomerRepository;
import com.example.retail_system_api.repo.RoleRepository;
import com.example.retail_system_api.repo.UserImageRepository;
import com.example.retail_system_api.repo.UserRepository;
import com.example.retail_system_api.service.CustomerService;
import com.example.retail_system_api.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private CustomersMapperImpl mapper;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserImageRepository userImageRepo;


    @Override
    @Transactional
    public ResponseEntity<ApiResponse<CustomerResponseDto>> register(CustomerRequestDto reqDto) {
        //1.Check exists customer by email
        boolean existsCustomer = customerRepo.existsByUser_Email(reqDto.getEmail());
        if(existsCustomer){
            throw new DuplicateResourceException("this customer already exists");
        }

        //2.Map data from Dto -> CustomerEntity
        CustomersEntity customer = mapper.DtoToEntity(reqDto);

        //3.set data in table user and save into db
        UsersEntity user = customer.getUser();
        user.setStatus(Status.ACTIVE);
        //set role
        RoleEntity roleUser = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("ROLE_USER not found"));
        user.setRole(roleUser);

        //set default userImage in UserImageTable
        UsersImageEntity usersImage = new UsersImageEntity();
        usersImage.setImageUrl("https://img.freepik.com/premium-vector/default-avatar-profile-icon-social-media-user-image-gray-avatar-icon-blank-profile-silhouette-vector-illustration_561158-3383.jpg");
        usersImage.setIsDefault(true);
        userImageRepo.save(usersImage);
        user.setUsersImage(usersImage);

        //save user data in to db
        userRepo.save(user);

        //5.Save entity to db
        CustomersEntity save = customerRepo.save(customer);

        //6.Map data from CustomerEntity -> Dto
        CustomerResponseDto dto = mapper.entityToDto(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(201,"created a new resource success",dto)
        );
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> showAllCustomer() {
        //1.get all data from db
        List<CustomersEntity> customers = customerRepo.findAll();
        if(customers.isEmpty()){
            throw new ResourceNotFoundException("No customers record");
        }

        //2.map data from customerEntity ->> Dto
        List<CustomerResponseDto> dtoList = new ArrayList<>();
        for(CustomersEntity cus : customers){
            CustomerResponseDto dto = mapper.entityToDto(cus);
            dtoList.add(dto);
        }
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",dtoList)
        );
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<CustomerResponseDto>> findCustomerById(Long id) {
        //1.find customer from db
        CustomersEntity customers = customerRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Customers not found"));

        //2.map data from customerEntity -> Dto
        CustomerResponseDto dto = mapper.entityToDto(customers);
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,"success",dto
        ));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<CustomerResponseDto>> updateCustomer(
            CustomerRequestDto reqDto, Long id) {

        // 1. find existing customer
        CustomersEntity customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        // 2. update ONLY customer fields
        customer.setFirstName(reqDto.getFirstname());
        customer.setLastName(reqDto.getLastname());
        customer.setDob(reqDto.getDob());
        customer.setAddress(reqDto.getAddress());
        customer.setPhone(reqDto.getPhone());
        customer.setUpdatedAt(LocalDateTime.now());

        // 3. save to update
        CustomersEntity saved = customerRepo.save(customer);

        // 4. map to dto
        CustomerResponseDto dto = mapper.entityToDto(saved);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Update customer successful", dto)
        );
    }



    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> removeCustomerById(Long id) {

        CustomersEntity customers = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("customer not found!"));

        UsersEntity users = userRepo.findById(customers.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("user not found!"));

        //update status
        users.setStatus(Status.INACTIVE);

        //save to db again
        userRepo.save(users);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "success",
                        "remove customer success"
                )
        );
    }

}
