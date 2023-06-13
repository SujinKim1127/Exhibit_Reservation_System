package com.ers.advice;

import com.ers.model.ResponseInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;



@RestControllerAdvice
public class ExhibitControllerAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResponseInfo> handleNotFoundExceptions(NoHandlerFoundException ex) {
        System.out.println("메시지" + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseInfo(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

}
