package com.xentn.shpee.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/***
 * @Description: admin controller
 *
 * @author: ibkon
 * @date: 2021/3/22
 * @version: 1.0
 */
@Controller
public class adminController {
    @RequestMapping("/admin")
    public String admin(HttpServletRequest request){
        return "admin/index";
    }
}
