package com.xentn.shpee.controller.content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/***
 * @Description: index controller
 *
 * @author: ibkon
 * @date: 2021/3/14
 * @version: 1.0
 */
@Controller
public class MainController {
    @GetMapping("/")
    public String index(){
        return "view/index";
    }

    @GetMapping("/install")
    public String install(){
        return "admin/install";
    }
}
