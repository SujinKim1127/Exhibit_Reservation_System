package com.ers.controller;

import com.ers.model.Exhibit;
import com.ers.model.ExhibitResponse;
import com.ers.model.ResponseInfo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteController {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    MessageSource messageSource;

    @DeleteMapping("/exhibit")
    public ResponseEntity<ResponseInfo> deleteExhibit(@RequestParam(value = "id", required = true) String exhibitId) {
        try {
            // DB
            String sql = "DELETE FROM exhibit WHERE exhibit_id = ?";
            jdbcTemplate.update(sql, exhibitId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).
                    body(new ResponseInfo(HttpStatus.NO_CONTENT.value(),
                            messageSource.getMessage("DeleteExhibit", null, null)));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseInfo(HttpStatus.BAD_REQUEST.value(),
                            messageSource.getMessage("BadRequest", null, null)));
        }
    }

}
