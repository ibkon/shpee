package top.yukino.shpee.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yukino.shpee.base.Super;
import top.yukino.shpee.bean.TUser;
import top.yukino.shpee.bean.TUserRole;

import javax.servlet.http.HttpServletRequest;
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
        //测试期间防止注册频率过快
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String  name    = request.getParameter("username");
        String  password    = request.getParameter("password");
        TUser   user    = new TUser(mapper);
        TUserRole tus	= new TUserRole(mapper);
        user.setNAME(name);
        user.setPASSWORD(password);
        tus.setNAME(name);
        tus.setRID("USER");
        tus.insert();
        Integer retVal  = null;
        retVal  = user.insert();
        if(retVal==null||retVal==0)
            return buildJson(1,"注册失败："+retVal,null);
        return buildJson(0,"注册成功",null);
    }
}
