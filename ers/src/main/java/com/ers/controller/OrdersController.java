package com.ers.controller;

import com.ers.model.OrderResponse;
import com.ers.model.ResponseInfo;
import com.ers.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class OrdersController {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    MessageSource messageSource;

    //주문 DELETE
    @DeleteMapping("/order")
    public ResponseEntity<ResponseInfo> deleteOrder(@RequestParam(value="id", required = true) String orderId) {
        try {
            String query = "DELETE FROM orders WHERE order_id = ?";
            jdbcTemplate.update(query, orderId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).
                    body(new ResponseInfo(HttpStatus.NO_CONTENT.value(),
                            messageSource.getMessage("DeleteOrder", null, null)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseInfo(HttpStatus.BAD_REQUEST.value(),
                            messageSource.getMessage("BadRequest", null, null)));
        }

    }

    //주문 GET
    @GetMapping("/order")
    public ResponseEntity<Object> getOrder(@RequestParam(value="id", required = true) String orderId) {
        String query = "SELECT * FROM orders WHERE order_id = ?";
        List<Orders> orders = jdbcTemplate.query(
                query,
                new RowMapper<Orders>() {
                    @Override
                    public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Orders orders = new Orders();
                        orders.setOrder_id(rs.getInt("order_id"));
                        orders.setUser_id(rs.getInt("user_id"));
                        orders.setExhibit_id(rs.getInt("exhibit_id"));
                        orders.setTitle(rs.getString("title"));
                        orders.setPrice(rs.getInt("price"));
                        orders.setPurchase_date(rs.getString("purchase_date"));
                        orders.setAddress(rs.getString("address"));
                        orders.setName(rs.getString("name"));
                        orders.setTel(rs.getString("tel"));
                        orders.setAmount(rs.getInt("amount"));
                        return orders;
                    }
                },
                orderId
        );

        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new com.ers.model.ResponseInfo(HttpStatus.NOT_FOUND.value(),
                    messageSource.getMessage("NotFoundOrder", null, null)));

        } else {
            Orders order = orders.get(0);
            return ResponseEntity.ok(order);
        }
    }

    //주문 POST
    @PostMapping("/order")
    public ResponseEntity<Object> addOrder(@RequestBody @Validated Orders orders) {
        int exhibitPrice;
        String exhibitTitle;
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

            //전시 제목 확인
            query = "SELECT title FROM exhibit WHERE exhibit_id = ?";
            exhibitTitle = jdbcTemplate.queryForObject(query, String.class, orders.getExhibit_id());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseInfo(HttpStatus.NOT_FOUND.value(),
                            messageSource.getMessage("NotFoundExhibit", null, null)));
        }
        try {
            //DB의 order 테이블에 저장
            String query = "INSERT INTO orders (`user_id`, `exhibit_id`, `title`, `price`, `purchase_date`, `address`, `name`,`tel`, `amount` ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            //주문 가격
            int totalPrice = exhibitPrice * orders.getAmount();
            //주문 날짜
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(dateFormatter);

            int rowsAffected = jdbcTemplate.update(query, orders.getUser_id(), orders.getExhibit_id(), exhibitTitle, totalPrice, formattedDate,
                    orders.getAddress(), orders.getName(), orders.getTel(), orders.getAmount());

            if (rowsAffected > 0) {
                //방금 생성한 주문의 order_id 가져오기
                String selectQuery = "SELECT LAST_INSERT_ID()";
                int generatedId = jdbcTemplate.queryForObject(selectQuery, Integer.class);

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new OrderResponse(generatedId));
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
