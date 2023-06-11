package com.ers.controller;

import com.ers.ResponseInfo;
import com.ers.model.LoginInfo;
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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


import java.io.IOException;
import java.sql.Connection;
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
        String login = "SELECT * FROM user WHERE userid = ?";

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
            java.sql.Date sqlDate = new java.sql.Date(user.getBirthdate().getTime());
            Connection con = null;
            // user_id 넣기
            String stme = "select COUNT(*) from user";
            int user_id = jdbcTemplate.queryForObject(stme, Integer.class);
            System.out.println(user_id);

            int result = jdbcTemplate.update(sql,user_id+1, user.getId(), user.getPassword(), user.getName(), user.getTel(), user.getAddress(), sqlDate, user.getEmail());
        } catch (Exception e){
            System.out.println(e);
        }
        return "signup_result";
    }

    public boolean checkLogin(String login, String loginPassword) {
        String query = "SELECT * FROM user WHERE id = ?";
        boolean existID = jdbcTemplate.query(query, rs -> {
            if (rs.next()) {
                String dbpw = rs.getString("password");
                if (dbpw.equals(loginPassword)) {
                    return true;
                }
            }
            return false;
        }, login);

        return existID;
    }


    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(LoginInfo loginInfo, Model model, HttpSession session, @CookieValue(value="REMEMBERID", required=false) Cookie rCookie) {
        if(session == null || session.getAttribute("authInfo") == null) {
            if(rCookie != null) {
                loginInfo.setUserid(rCookie.getValue());
                loginInfo.setRememberid(true);
            } }
        return "signin";
    }


    @PostMapping("/signin/submit")
    public String loginResult(@Validated LoginInfo loginInfo, Errors errors, HttpSession session, HttpServletResponse response) {
        if(checkLogin(loginInfo.getUserid(), loginInfo.getPwd())){
            if(errors.hasErrors()) {
                return "login";
            }
            session.setAttribute("authInfo", loginInfo);
            Cookie rememberCookie = new Cookie("REMEMBERID", loginInfo.getUserid());
            rememberCookie.setPath("/login");
            if(loginInfo.getRememberid()){
                rememberCookie.setMaxAge(60*60*24*30);
            } else{
                rememberCookie.setMaxAge(0);
            }
            response.addCookie(rememberCookie);
            return "signin_result";
        }
        else{
            errors.rejectValue("userid", "invalid");
            errors.rejectValue("pwd", "nomatch");
            return "signin";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        if(session != null) session.invalidate();
        return "logout";
    }






}
