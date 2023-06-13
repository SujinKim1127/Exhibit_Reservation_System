package com.ers.controller;

import com.ers.model.Exhibit;
import com.ers.model.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
public class ExhibitController {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    MessageSource messageSource;

    //전시 목록 전체 조회
    @GetMapping("/exhibitlist")
    public List<Exhibit> exhibitlist() {
        return jdbcTemplate.query("SELECT * FROM exhibit",
                new RowMapper<Exhibit>() {
                    @Override
                    public Exhibit mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Exhibit exhibit = new Exhibit();
                        exhibit.setExhibit_id(rs.getInt("exhibit_id"));
                        exhibit.setTitle(rs.getString("title"));
                        exhibit.setContents(rs.getString("contents"));
                        exhibit.setImg(rs.getString("img"));
                        exhibit.setStart_date(rs.getString("start_date"));
                        exhibit.setEnd_date(rs.getString("end_date"));
                        exhibit.setPrice(rs.getInt("price"));
                        exhibit.setOwner(rs.getString("owner"));
                        exhibit.setLikes(rs.getInt("likes"));
                        return exhibit;
                    }
                });
    }

    //전시 상세 조회
    @GetMapping("/exhibit")
    public ResponseEntity<Object> exhibitdetail(@RequestParam(value="id", required = true) String exhibitId) {
        String query = "SELECT * FROM exhibit WHERE exhibit_id = " + exhibitId;
        List<Exhibit> exhibits = jdbcTemplate.query(query,
                new RowMapper<Exhibit>() {
                    @Override
                    public Exhibit mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Exhibit exhibit = new Exhibit();
                        exhibit.setExhibit_id(rs.getInt("exhibit_id"));
                        exhibit.setTitle(rs.getString("title"));
                        exhibit.setContents(rs.getString("contents"));
                        exhibit.setImg(rs.getString("img"));
                        exhibit.setStart_date(rs.getString("start_date"));
                        exhibit.setEnd_date(rs.getString("end_date"));
                        exhibit.setPrice(rs.getInt("price"));
                        exhibit.setOwner(rs.getString("owner"));
                        exhibit.setLikes(rs.getInt("likes"));
                        return exhibit;
                    }
                });

        if (exhibits.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfo(HttpStatus.NOT_FOUND.value(),
                    messageSource.getMessage("NotFound", null, null)));

        } else {
            Exhibit exhibit = exhibits.get(0);
            return ResponseEntity.ok(exhibit);
        }

    }
}
