package com.ers.controller;

import com.ers.ResponseInfo;
import com.ers.model.ExhibitResponse;
import com.ers.model.Likes;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class LikeController {
    @Autowired
    MessageSource messageSource;

    private List<Likes> likesList;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/like")
    public ResponseEntity<Object> addLike(@RequestBody @Validated Likes like, HttpServletResponse response) throws IOException {
//        try {
//            // 유저 존재 파악
//            String query = "SELECT * FROM user WHERE id = ?";
//            boolean existID = jdbcTemplate.query(query, rs -> {
//                if (rs.next()) {
//                    return true;
//                }
//                return false;
//            }, like.getUser_id());
//            if(!existID) throw new Exception();
//        } catch (Exception e){
//            System.out.println(e);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new com.ers.ResponseInfo(HttpStatus.NOT_FOUND.value(),
//                            messageSource.getMessage("NotFoundUser", null, null)));
//        }

        try {
            String query = "INSERT INTO likes VALUES(?,?,?,?)";
            String stme = "select COUNT(*) from likes";
            int wish_id = jdbcTemplate.queryForObject(stme, Integer.class);

            int generatedId = jdbcTemplate.update(query, wish_id +1, like.getUser_id(), like.getExhibit_id(), like.getTitle() );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ExhibitResponse(generatedId));

        } catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseInfo(HttpStatus.BAD_REQUEST.value(),
                            messageSource.getMessage("BadRequest", null, null)));
        }
    }

}
