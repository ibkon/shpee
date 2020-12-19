package com.xentn.shpee.control.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xentn.shpee.base.Super;

import javax.servlet.http.HttpServletRequest;

/***
 class name:UserInfo
 edit user:Sagiri
 create time:2020/7/6
 TODO:
 */
@Controller
@RequestMapping("admin/user")
public class UserInfo extends Super {
    @GetMapping("list")
    public String UserList(){
        return "admin/resources_info/user_list";
    }
    @GetMapping("admin/get-user-info")
    public String getUserInfo(HttpServletRequest request){
        return "admin/resources_info/UserInfo";
    }

}
