package top.yukino.shpee.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yukino.shpee.base.Super;
import top.yukino.shpee.bean.TUser;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Map;

/***
 * 注册
 */
@Controller
public class Registered extends Super {

    @RequestMapping("/registered")
    public String   registered(HttpServletRequest request,Map<String,Object> val){
        return "registered";
    }

    @RequestMapping("/registered/info")
    @ResponseBody
    public Map<String,Object>   registeredInfo(HttpServletRequest request){
        String  name    = request.getParameter("username");
        String  password    = request.getParameter("password");
        TUser   user    = mapper.selsctTUser(name);
        if(user!=null){
            return buildJson(1,"注册失败",null);
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
