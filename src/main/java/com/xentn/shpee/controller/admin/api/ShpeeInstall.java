package com.xentn.shpee.controller.admin.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***
 * @Description: install
 *
 * @author: ibkon
 * @date: 2021/3/22
 * @version: 1.0
 */
@Controller
public class ShpeeInstall {
    @ResponseBody
    @RequestMapping("/admin/install")
    public Map<String,Object> install(HttpServletRequest request){
        return null;
    }
}
