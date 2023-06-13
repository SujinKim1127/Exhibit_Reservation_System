package com.ers.advice;


import com.ers.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class DBHandler {
    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseInfo> handleSQLException(SQLException ex) {
        //에러 정보 로깅
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                      messageSource.getMessage("DBError", null, null)  )
        );
    }
}
