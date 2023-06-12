package com.ers.controller;

import com.ers.model.Exhibit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class HomeController {
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/")
    public String home(Model model) {
        String url = "http://localhost:8080/exhibitlist";
        ResponseEntity<String> response_ = restTemplate.getForEntity(url, String.class);
        String json = response_.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Exhibit> exhibitList = objectMapper.readValue(json, new TypeReference<List<Exhibit>>() {});
            model.addAttribute("exhibitlist", exhibitList);
        } catch (Exception e) {
            // 파싱 중 오류가 발생한 경우 처리
            e.printStackTrace();
        }

        return "main";
    }

    @GetMapping("/exhibitdetail/{exhibit_id}")
    public String exhibitDetail(Model model, @PathVariable("exhibit_id") String exhibitId) {
        System.out.println("전시 상세 조회" + exhibitId);
        String url = "http://localhost:8080/exhibit?id=" + exhibitId;

        ResponseEntity<String> response_ = restTemplate.getForEntity(url, String.class);
        String json = response_.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Exhibit exhibit = objectMapper.readValue(json, new TypeReference<Exhibit>() {});
            model.addAttribute("exhibit", exhibit);
        } catch (Exception e) {
            // 파싱 중 오류가 발생한 경우 처리
            e.printStackTrace();
        }

        return "exhibitdetail";
    }

}
