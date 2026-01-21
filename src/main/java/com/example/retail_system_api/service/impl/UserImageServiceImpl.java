package com.example.retail_system_api.service.impl;

import com.example.retail_system_api.entity.UsersImageEntity;
import com.example.retail_system_api.exception.ResourceNotFoundException;
import com.example.retail_system_api.repo.UserImageRepository;
import com.example.retail_system_api.repo.UserRepository;
import com.example.retail_system_api.service.UserImageService;
import com.example.retail_system_api.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class UserImageServiceImpl implements UserImageService {

    @Autowired
    private UserImageRepository userImageRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private Path uploadPath;

    @Override
    public ResponseEntity<ApiResponse<List<UsersImageEntity>>> showAllProfile() {
        List<UsersImageEntity> getAll = userImageRepo.findAll();
        if(getAll.isEmpty()){
            throw new ResourceNotFoundException("no user image record");
        }

        return ResponseEntity.ok().body(new ApiResponse<>(
                200,"success",getAll
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<UsersImageEntity>> showProfileById(Long id) {
        UsersImageEntity find = userImageRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("user image not found"));

        return ResponseEntity.ok().body(new ApiResponse<>(
                200,"success",find
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<UsersImageEntity>> updateProfile(UsersImageEntity usersImage, Long id, MultipartFile file) throws IOException {
        //1.find UserImage
        UsersImageEntity findImage = userImageRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("user image not found"));

        //2.update image user
        if(file!=null){
            //set default filename
            String filename = UUID.randomUUID()+ "_" + file.getOriginalFilename();

            //save filename to
            Path filePath = uploadPath.resolve(filename);
            file.transferTo(filePath);

            //save image path
            String httpPath = "http://localhost:8080";
            findImage.setImageUrl(httpPath +"/images/" + filename);
        }
        findImage.setIsDefault(false);

        //save to db for update
        UsersImageEntity saved = userImageRepo.save(findImage);

        return ResponseEntity.ok().body(new ApiResponse<>(
           200,"update profile success",saved
        ));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> removeProfile(Long id){
        //1.find userImage by id
        UsersImageEntity findImage = userImageRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("user image not found!"));

        //2.change isDefault and make image to default image
        findImage.setImageUrl("https://img.freepik.com/premium-vector/default-avatar-profile-icon-social-media-user-image-gray-avatar-icon-blank-profile-silhouette-vector-illustration_561158-3383.jpg");
        findImage.setIsDefault(true);

        //3.Save
        userImageRepo.save(findImage);
        return ResponseEntity.ok().body(new ApiResponse<>(
                200, "delete profile success", "success"
        ));
    }
}
