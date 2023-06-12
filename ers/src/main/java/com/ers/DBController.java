package com.ers;

import ch.qos.logback.core.spi.ErrorCodes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class DBController {

    private JdbcTemplate jdbcTemplate;

    @JsonIgnore
    private ErrorCodes message;

//    @PostMapping("/signup/submit")
//    public ResponseEntity<HttpResponse.ResponseInfo> addUser() throws IOException {
//        try{
//
//        }
//    }

}
