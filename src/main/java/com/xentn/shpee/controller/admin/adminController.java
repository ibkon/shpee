package com.xentn.shpee.controller.admin;

import com.xentn.shpee.bean.tool.Supper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***
 * @Description: admin controller
 *
 * @author: ibkon
 * @date: 2021/3/22
 * @version: 1.0
 */
@Controller
public class adminController extends Supper {
    @RequestMapping("/admin")
    public String admin(HttpServletRequest request, Map<String,Object> thValue){
        thValue.put("shpee_name",getConfigMapper().getConfig("shpee_name"));
        thValue.put("user_name",getUserDetails().getUsername());
        return "admin/index";
    }
}
