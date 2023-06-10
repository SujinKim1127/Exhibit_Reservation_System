package com.ers.controller;

import com.ers.ResponseInfo;
import com.ers.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/showapi")
    public String showApi(Model model){
        return "showapi";
    }

    private ArrayList<User> userArrayList;

    public HelloController() {
        userArrayList = new ArrayList<User>();
    }


//    @RequestMapping(value = "/signup", method = RequestMethod.GET)
//    public String signup(User user, Model model, HttpSession session) {
//        return "signup";
//    }

    @GetMapping("/signup")
    public String signup(User user){
        return "signup";
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/users")
    public List<User> users() { return userArrayList; }


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
            String sql = "INSERT INTO user VALUES(?,?,?,?,?,?,?,?)";
            System.out.println(user.getBirthdate());
            java.sql.Date sqlDate = new java.sql.Date(user.getBirthdate().getTime());
            System.out.println(sqlDate);
            int result = jdbcTemplate.update(sql,3, user.getId(), user.getPassword(), user.getName(), user.getTel(), user.getAddress(), sqlDate, user.getEmail());
        } catch (Exception e){
            System.out.println(e);
        }
        return "signup_result";
    }


}
