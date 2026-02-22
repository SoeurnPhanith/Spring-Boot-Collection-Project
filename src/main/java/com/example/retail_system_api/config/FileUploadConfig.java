package com.example.retail_system_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileUploadConfig {

    //get value from config
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Bean
    public Path uploadPath(){
        //get root directory to store image
        Path path = Paths.get(System.getProperty("user.dir"), uploadDir);

        //check exists folder or not
        try {
            if(!Files.exists(path)){
                //create  upload folder
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return path;
    }

}
