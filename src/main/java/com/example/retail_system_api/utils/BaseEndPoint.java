package com.example.retail_system_api.utils;

import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public interface BaseEndPoint {

    String endPoint = "${spring.end-point}";

}
