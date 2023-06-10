package com.ers;

import com.ers.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;

public class UserController {
    private JdbcTemplate jdbcTemplate;

    private ArrayList<User> userArrayList;

    public UserController() {
        userArrayList = new ArrayList<User>();
    }

    @GetMapping("/signup")
    public String signup(User user){
        return "signup";
    }


    @PostMapping("/signup/submit")
    public String submit(@Validated User user, Errors errors) {
        for (User item : userArrayList) {
            if (item.getId().equals(user.getId())) {
                errors.rejectValue("id", "duplicated");
            }
        }
        if (errors.hasErrors()) {
            return "signup";
        }
        userArrayList.add(user);
        try {
            String sql = "INSERT INTO user VALUES(?,?,?,?,?,?,?)";
            int result = jdbcTemplate.update(sql, user.getId(), user.getPassword(), user.getName(), user.getTel(), user.getAddress(), user.getBirthdate(), user.getEmail());
        } catch (Exception e){
            System.out.println(e);
        }
        return "signup_result";

    }
}