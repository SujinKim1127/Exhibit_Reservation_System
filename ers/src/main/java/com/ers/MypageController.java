package com.ers;

import com.ers.model.Likes;
import com.ers.model.Mypage;
import com.ers.model.Orders;
import com.ers.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MypageController {
    @Autowired
    MessageSource messageSource;
    private List<User> userList;
    private List<Mypage> mypageList;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @GetMapping("/mypage")
    public List<Mypage> mypage(HttpSession session) {

        String loginID = session.getAttribute("loginID").toString();
        String query = "SELECT user_id FROM user WHERE id = ?";
        int user_id = jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            return rs.getInt("user_id");
        }, loginID);

        mypageList = new ArrayList<Mypage>();

        mypageList.add(new Mypage(
                jdbcTemplate.query("select * from likes where user_id=?",
                        new RowMapper<Likes>(){
                            @Override
                            public Likes mapRow(ResultSet rs, int rowNum) throws SQLException {
                                Likes like = new Likes();
                                like.setWish_id(rs.getInt("wish_id"));
                                like.setUser_id(rs.getInt("user_id"));
                                like.setExhibit_id(rs.getInt("exhibit_id"));
                                like.setTitle(rs.getString("title"));
                                return like;
                            }
                        }
                        ,user_id)
                , jdbcTemplate.query("select * from orders where user_id=?",
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
                }
        , user_id)));

        return mypageList;
    }
}
