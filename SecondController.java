package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecondController {
    @GetMapping("/random-quote")
    public String randomQuote(Model model){
        String [] quotes={
                "행복은 습관이다. 그것을 몸에 지니라."+"-허버드-",
                "중요한 건 꺾이지 않는 마음"+"-deft-",
                "답답하면 니들이 뛰던가"+"-기성용-",
                "4chinese can't win"+"-khan-"
        };
        int randInt =(int)(Math.random()*quotes.length);
        model.addAttribute("randomQuote",quotes[randInt]);
        return "quote";
    }


}


