package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String meetyou(Model model){
        model.addAttribute("username","CHIJUN");
        return "greetings";
    }
    @GetMapping("/bye")
    public String seeyounext(Model model){
         model.addAttribute("nickname","치준");
         return "goodbye";
    }

}
