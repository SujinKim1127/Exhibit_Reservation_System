package com.ers.controller;

import com.ers.model.ResponseInfo;
import com.ers.model.Likes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LikeController {
    @Autowired
    MessageSource messageSource;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/like")
    public ResponseEntity<Object> addLike(@RequestBody @Validated Likes like) {
        String exhibitTitle;
        //유저가 존재하는지 확인
        try {
            String query = "SELECT COUNT(*) FROM user WHERE user_id = ?";
            int countUser = jdbcTemplate.queryForObject(query, Integer.class, like.getUser_id());
            if (countUser == 0) throw new Exception();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseInfo(HttpStatus.NOT_FOUND.value(),
                            messageSource.getMessage("NotFoundUser", null, null)));
        }
        //전시가 존재하는지 확인
        try {
            String query = "SELECT COUNT(*) FROM exhibit WHERE exhibit_id = ?";
            int countExhibit = jdbcTemplate.queryForObject(query, Integer.class, like.getExhibit_id());
            if (countExhibit == 0) throw new Exception();

            //전시 제목 확인
            query = "SELECT title FROM exhibit WHERE exhibit_id = ?";
            exhibitTitle = jdbcTemplate.queryForObject(query, String.class, like.getExhibit_id());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseInfo(HttpStatus.NOT_FOUND.value(),
                            messageSource.getMessage("NotFoundExhibit", null, null)));
        }

        //DB의 like 테이블에 저장 혹은 취소
        try {
            String query = "SELECT COUNT(*) FROM likes WHERE user_id = ? AND exhibit_id = ?";
            int countLikes = jdbcTemplate.queryForObject(query, Integer.class, like.getUser_id(), like.getExhibit_id());

            if (countLikes > 0) { //이미 좋아요 존재
                query = "DELETE FROM likes WHERE user_id = ? AND exhibit_id = ?";
                jdbcTemplate.update(query, like.getUser_id(), like.getExhibit_id());
                return ResponseEntity.noContent().build();

            } else { //새로 좋아요
                query = "INSERT INTO likes (`user_id`, `exhibit_id`, `title`) VALUES (?, ?, ?)";
                jdbcTemplate.update(query, like.getUser_id(), like.getExhibit_id(), exhibitTitle);
                return ResponseEntity.status(HttpStatus.CREATED).
                        body(new ResponseInfo(HttpStatus.CREATED.value(),
                                messageSource.getMessage("AddLike", null, null)));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseInfo(HttpStatus.BAD_REQUEST.value(),
                            messageSource.getMessage("BadRequest", null, null)));
        }
    }

}
