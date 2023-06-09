package com.ers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/showapi")
    public String showApi(Model model){
        return "showapi";
    }
}
