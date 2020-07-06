package top.yukino.shpee.control.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.yukino.shpee.base.Super;

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
}
