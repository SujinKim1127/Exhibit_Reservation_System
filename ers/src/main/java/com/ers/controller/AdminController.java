package com.ers.controller;

import com.ers.model.Exhibit;
import com.ers.model.LoginInfo;
import com.ers.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Connection;

@Controller
public class AdminController {
    private JdbcTemplate jdbcTemplate;

    public AdminController() {
        String login = "SELECT * FROM user WHERE userid = ?";
    }
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(value="/admin", method = RequestMethod.GET)
    public String admin(Exhibit exhibit, Model model, HttpSession session) {
        String loginID = session.getAttribute("loginID").toString();
        if(loginID.equals("admin")){
            return "admin";
        }
        return "redirect:/signin";
    }

    @PostMapping("/admin/submit")
    public String submit(@Validated Exhibit exhibit, Errors errors) {
        if(errors.hasErrors()) { return "admin"; }
        try {
            String sql = "INSERT INTO exhibit VALUES(?,?,?,?,?,?,?,?,?)";

            String stme = "select COUNT(*) from exhibit";
            int exhibit_id = jdbcTemplate.queryForObject(stme, Integer.class);
            jdbcTemplate.update(sql, exhibit_id+1, exhibit.getTitle(), exhibit.getContents(),
                    exhibit.getImg(), exhibit.getStart_date(), exhibit.getEnd_date(), exhibit.getPrice(), exhibit.getOwner(), 0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "admin_result";
    }
}
