package top.yukino.shpee.control;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yukino.shpee.bean.TUpload;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/***
 * 注册
 */
@Controller
public class Registered extends Super{

    @RequestMapping("/registered")
    public String   registered(HttpServletRequest request,Map<String,Object> val){
        return "registered";
    }

    @RequestMapping("/registered/info")
    @ResponseBody
    public String   registeredInfo(HttpServletRequest request){
        String  name    = request.getParameter("username");
        String  password    = request.getParameter("password");
        String  role        = "ROLE_ADMIN";
        if(name==null||password==null||role==null){
            return "注册失败";
        }
        List<Map<String,Object>>    lMap;
        String  uuid;
        lMap=mapper.select("select * from T_USER where NAME='"+name+"'");
        if(lMap!=null&&lMap.size()>0){
            uuid=UUID.randomUUID().toString().replaceAll("-","");
            mapper.insert("insert into T_ROLE(RID,ROLE) values('"+ uuid +"','"+role+"')");
            mapper.insert("insert into T_USER_ROLE(NAME,RID) values('"+name+"','"+uuid+"')");
            return "用户"+name+"添加新权限成功";
        }
        else {
            uuid=UUID.randomUUID().toString().replaceAll("-","");
            password=new BCryptPasswordEncoder().encode(password);
            mapper.insert("insert into T_USER(NAME,PASSWORD) values('"+name+"','"+password+"')");
            mapper.insert("insert into T_ROLE(RID,ROLE) values('"+ uuid +"','"+role+"')");
            mapper.insert("insert into T_USER_ROLE(NAME,RID) values('"+name+"','"+uuid+"')");
            return "添加用户成功";
        }
    }
}
