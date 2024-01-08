package com.example.blogapplication.Exceptions;

import com.example.blogapplication.Payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
    String message=ex.getMessage();
    ApiResponse apiResponse=new ApiResponse(message,false);
    return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler()
    public ResponseEntity<Map<String,String>> HandleMethodArgsNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> resp= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
               String fieldName=((FieldError)error).getField();
               String message=error.getDefaultMessage();
               resp.put(fieldName,message);
        });

        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }
}
