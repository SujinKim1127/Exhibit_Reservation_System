package com.ers.controller;

import com.ers.model.LoginInfo;
import com.ers.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.sql.Connection;
import java.util.ArrayList;

@Controller
public class UserController {
    private JdbcTemplate jdbcTemplate;

    private ArrayList<User> userArrayList;

    public UserController() {
        userArrayList = new ArrayList<User>();
        String login = "SELECT * FROM user WHERE userid = ?";
    }

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
        String query = "SELECT * FROM user WHERE id = ?";
        boolean existID = jdbcTemplate.query(query, rs -> {
            if (rs.next()) {
                return true;
            }
            return false;
        }, user.getId());

        if (existID) {
            errors.rejectValue("id", "duplicated");
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

            int result = jdbcTemplate.update(sql,user_id, user.getId(), user.getPassword(), user.getName(), user.getTel(), user.getAddress(), sqlDate, user.getEmail());
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
            }
        }
        return "signin";
    }


    @PostMapping("/signin/submit")
    public String loginResult(@Validated LoginInfo loginInfo, Errors errors, HttpSession session, HttpServletResponse response) {
        if(checkLogin(loginInfo.getUserid(), loginInfo.getPwd())){
            session.setAttribute("loginID", loginInfo.getUserid());
            if(errors.hasErrors()) {
                return "signin";
            }
            session.setAttribute("authInfo", loginInfo);
            Cookie rememberCookie = new Cookie("REMEMBERID", loginInfo.getUserid());

            String query = "SELECT user_id FROM user WHERE id = ?";
            int user_id = jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
                return rs.getInt("user_id");
            }, loginInfo.getUserid());

            Cookie loginCookie = new Cookie("USERID", String.valueOf(user_id));
            rememberCookie.setPath("/signin");
            loginCookie.setPath("/");
            if(loginInfo.getRememberid()){
                rememberCookie.setMaxAge(60*60*24*30);
            } else{
                rememberCookie.setMaxAge(0);
            }
            response.addCookie(rememberCookie);
            response.addCookie(loginCookie);
            return "signin_result";
        }
        else{
            errors.rejectValue("userid", "invalid");
            errors.rejectValue("pwd", "nomatch");
            return "signin";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        Cookie loginCookie = new Cookie("USERID", "-1");
        loginCookie.setPath("/");
        loginCookie.setMaxAge(0);
        response.addCookie(loginCookie);
        if(session != null) session.invalidate();
        return "logout";
    }

    @RequestMapping("/userInfo")
    public String userinfo(@PathVariable("id") HttpSession session){
        if(session.getAttribute("authInfo") == null) return "redirect:/signin";
        return "error";
    }

    @RequestMapping("/userInfo/{id}")
    public String userinfo(@PathVariable("id") String userid, User user, HttpSession session) {
        if(session.getAttribute("authInfo") == null) return "redirect:/signin";
        if(user == null) user = new User();
        String loginID = session.getAttribute("loginID").toString();
        String query = "SELECT * FROM user WHERE id = ?";
        user = jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            User u = new User();
            u.setId(rs.getString("id"));
            u.setName(rs.getString("name"));
            u.setPassword(rs.getString("password"));
            u.setTel(rs.getString("tel"));
            u.setEmail(rs.getString("email"));
            u.setAddress(rs.getString("address"));
            u.setBirthdate(rs.getDate("birthdate"));
            return u;
        }, loginID);
        session.setAttribute("loginUser", user);
        return "userinfo";
    }

    @RequestMapping("/modifyUserInfo")
    public String modifyuserinfo(User user, HttpSession session) {
        if(user == null) user = new User();
        String loginID = session.getAttribute("loginID").toString();
        String query = "SELECT * FROM user WHERE id = ?";
        user = jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            User u = new User();
            u.setId(rs.getString("id"));
            u.setName(rs.getString("name"));
            u.setPassword(rs.getString("password"));
            u.setTel(rs.getString("tel"));
            u.setEmail(rs.getString("email"));
            u.setAddress(rs.getString("address"));
            u.setBirthdate(rs.getDate("birthdate"));
            return u;
        }, loginID);
        session.setAttribute("loginUser", user);

        return "modifyuserinfo"; }

    @PostMapping("/submituserinfo")
    public String modifyUserInfoResult(@Validated User user, Errors errors, HttpSession session) {
        if(errors.hasErrors()) {
            System.out.println(errors);
            return "modifyuserinfo";
        }
        if(user != null) {
            String loginID = session.getAttribute("loginID").toString();
            String edit = "UPDATE user SET id=?, password=?, name=?,tel=?, address=?, birthdate=?, email=? WHERE id=?";

            java.sql.Date sqlDate = new java.sql.Date(user.getBirthdate().getTime());
            user.setBirthdate(sqlDate);
            jdbcTemplate.update(edit, user.getId(), user.getPassword(), user.getName(), user.getTel(), user.getAddress(),sqlDate, user.getEmail(), loginID);
        }
        return "modifyinfo";
    }
}