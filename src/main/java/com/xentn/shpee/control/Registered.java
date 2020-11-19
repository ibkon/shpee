package com.xentn.shpee.control;

import com.xentn.shpee.bean.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xentn.shpee.base.Super;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Map;

/***
 * @Author Sagiri
 * @Date2020/5/3
 * 用户注册处理
 */
@Controller
public class Registered extends Super {
    /**
     * @Author Sagiri
     * @Description User registration controller
     * @Date 0:47 2020/5/3
     * @Param [request, val]
     * @return java.lang.String
     **/
    @GetMapping("/registered")
    public String   registered(HttpServletRequest request){
        return "registered";
    }

    @ResponseBody
    @PostMapping("/registered/info")
    public Map<String,Object>   registeredInfo(HttpServletRequest request){
        String  name    = request.getParameter("username");
        String  password    = request.getParameter("password");
        TUser user    = null;
        try {
            user    = mapper.selectTUser(buildMap("name",name)).get(0);
        }catch (ArrayIndexOutOfBoundsException e){
            return buildJson(1,"注册失败：",null);
        }
        user.setNAME(name);
        user.setPASSWORD(password);
        user.setUPTIME(new Timestamp(System.currentTimeMillis()));
        if(mapper.insertTUser(user)==1&&mapper.insertTUserRole(name,"USER")==1){
            return buildJson(0,"注册成功",null);
        }
        return buildJson(1,"注册失败：",null);
    }
}
