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

    private List<Likes> Likes = new ArrayList<Likes>();
    private List<Orders> Orders = new ArrayList<Orders>();



    public MypageController() {
    }
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

        System.out.println("yserid "+user_id);

        String orderstmt = "select * from orders where user_id= ?";
        List<Map<String, Object>> orders = jdbcTemplate.queryForList(orderstmt, user_id);

        for (Map<String, Object> order : orders) {
            int orderId = (int) order.get("order_id");
            int userId = (int) order.get("user_id");
            int exhibitId = (int) order.get("exhibit_id");
            int price = (int) order.get("price");
            String purchaseDate = order.get("purchase_date").toString();
            String address = (String) order.get("address");
            String name = (String) order.get("name");
            String tel = (String) order.get("tel");
            int amount = (int) order.get("amount");

            Orders newOrder = new Orders(orderId, userId, exhibitId, price, purchaseDate, address, name, tel, amount);
            Orders.add(newOrder);
        }
        mypageList = new ArrayList<Mypage>();

        String likestmt = "select * from likes where user_id= ?";
        List<Map<String, Object>> likes = jdbcTemplate.queryForList(likestmt, user_id);

        for (Map<String, Object> like : likes) {
            int wishId = (int) like.get("wish_id");
            int userId = (int) like.get("user_id");
            int exhibitId = (int) like.get("exhibit_id");

            Likes newLike = new Likes(wishId, userId, exhibitId);
            Likes.add(newLike);
        }

        mypageList.add(new Mypage(
                jdbcTemplate.query("select * from likes where user_id=?",
                        new RowMapper<Likes>(){
                            @Override
                            public Likes mapRow(ResultSet rs, int rowNum) throws SQLException {
                                Likes like = new Likes(rs.getInt("wish_id"), rs.getInt("user_id"), rs.getInt("exhibit_id"));
                                return like;
                            }
                        }
                        ,user_id)
                , jdbcTemplate.query("select * from orders where user_id=?",
                new RowMapper<Orders>() {
                    @Override
                    public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Orders order = new Orders(rs.getInt("order_id"), rs.getInt("user_id")
                                ,rs.getInt("exhibit_id"), rs.getInt("price")
                                , rs.getString("purchase_date"), rs.getString("address")
                                , rs.getString("name"), rs.getString("tel"), rs.getInt("amount"));
                        return order;
                    }
                }
        , user_id)));

        return mypageList;
    }
}
