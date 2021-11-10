package com.xentn.shpee.controller.admin;

import com.xentn.shpee.bean.tool.Supper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class adminUser extends Supper {

    @GetMapping("/admin/user/group")
    private String userGroup(){

        return "/admin/user/userGroup";
    }
}
