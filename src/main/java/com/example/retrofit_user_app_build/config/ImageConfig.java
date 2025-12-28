package com.example.retrofit_user_app_build.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //tell spring this is config class
public class ImageConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ResourceHandlerRegistry registry → object ដែល Spring Boot ផ្តល់,
        // ដើម្បី register resource handlers ថ្មី

        registry.addResourceHandler("/uploads/**")
                // បង្ហាញ pattern នៃ URL ដែល client អាច access image បាន
                // example: http://localhost:8080/uploads/abc.jpg

                 .addResourceLocations(
                        "file:" + System.getProperty("user.dir") + "/uploads/"
                );
        // បង្ហាញ ទីតាំង file system ដែល resource ត្រូវបានរក្សាទុក
        // example: /home/user/project/uploads/
    }
}
