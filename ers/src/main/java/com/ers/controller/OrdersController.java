package com.ers.controller;

import com.ers.ResponseInfo;
import com.ers.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class OrdersController {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    MessageSource messageSource;

    //주문 POST
    @PostMapping("/order")
    public ResponseEntity<ResponseInfo> addOrder(@RequestBody @Validated Orders orders) {
        int exhibitPrice;
        //유저가 존재하는지 확인
        try {
            String query = "SELECT COUNT(*) FROM user WHERE user_id = ?";
            int countUser = jdbcTemplate.queryForObject(query, Integer.class, orders.getUser_id());
            if (countUser == 0) throw new Exception();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseInfo(HttpStatus.NOT_FOUND.value(),
                            messageSource.getMessage("NotFoundUser", null, null)));
        }
        //전시가 존재하는지 확인
        try {
            String query = "SELECT COUNT(*) FROM exhibit WHERE exhibit_id = ?";
            int countExhibit = jdbcTemplate.queryForObject(query, Integer.class, orders.getExhibit_id());
            if (countExhibit == 0) throw new Exception();

            //전시 가격 확인
            query = "SELECT price FROM exhibit WHERE exhibit_id = ?";
            exhibitPrice = jdbcTemplate.queryForObject(query, Integer.class, orders.getExhibit_id());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseInfo(HttpStatus.NOT_FOUND.value(),
                            messageSource.getMessage("NotFoundExhibit", null, null)));
        }
        try {
            //DB의 order 테이블에 저장
            String query = "INSERT INTO orders (`user_id`, `exhibit_id`, `price`, `purchase_date`, `address`, `name`,`tel`, `amount` ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            //주문 가격
            int totalPrice = exhibitPrice * orders.getAmount();
            //주문 날짜
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(dateFormatter);

            int rowsAffected = jdbcTemplate.update(query, orders.getUser_id(), orders.getExhibit_id(), totalPrice, formattedDate,
                    orders.getAddress(), orders.getName(), orders.getTel(), orders.getAmount());

            if (rowsAffected > 0) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseInfo(HttpStatus.CREATED.value(),
                                messageSource.getMessage("Created", null, null)));
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseInfo(HttpStatus.BAD_REQUEST.value(),
                            messageSource.getMessage("BadRequest", null, null)));
        }

    }
}
