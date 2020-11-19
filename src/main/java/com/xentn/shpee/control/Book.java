package com.xentn.shpee.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Book {

    @ResponseBody
    @GetMapping("/book")
    public String ManPage(){
        return "search";
    }
}
