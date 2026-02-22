package com.example.retail_system_api.exception;

import com.example.retail_system_api.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandlerException {

    //handle global exception
    @ExceptionHandler(MethodArgumentNotValidException .class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String,String> globalError = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            globalError.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(globalError);
    }

    /*
        -ex.getBindingResult() : get result error
        -getFieldErrors() : get field na deal error
        -forEach(err->{}) : loop yk error message muy muy
        -fieldError.getField() : yk filed deal error
        -fieldError.getDefaultMessage() : yk message deal yg komnot knong DTO nos

    */

    //handle resourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>(
                        404, ex.getMessage(),null
                )
        );
    }

    //Handle duplicate resource exception
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateException(DuplicateResourceException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiResponse<>(
                        409, exception.getMessage(), null
                )
        );
    }


}
