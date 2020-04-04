package top.yukino.shpee.control;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
        if(getmConfig("registeredBackground")==null){
            setmConfig("registeredBackground","512467649B974414804A4824A27BD620");
        }
        List<Map<String, Object>> lMaps = mapper.select(
                "SELECT path,hash,type FROM T_UPLOAD WHERE uid='"+getmConfig("registeredBackground")+"'");
        if(lMaps==null||lMaps.size()==0){
            System.err.println("找不到注册背景图片");
        }
        String path, hash, type;
        path = lMaps.get(0).get("PATH").toString();
        hash = lMaps.get(0).get("HASH").toString();
        type = lMaps.get(0).get("TYPE").toString();
        String css="background-image: url('"+path.replaceAll("upload", "static") + "/" + DigestUtils.md5Hex(hash).toUpperCase() + "."
                + type+"')";
        val.put("registeredBackground",css);
        return "registered";
    }

    @RequestMapping("/registered/info")
    @ResponseBody
    public String   registeredInfo(HttpServletRequest request){
        String  name    = request.getParameter("name");
        String  password    = request.getParameter("password");
        String  role        = request.getParameter("role");
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
