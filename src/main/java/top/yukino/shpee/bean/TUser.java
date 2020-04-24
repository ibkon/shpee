package top.yukino.shpee.bean;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import top.yukino.shpee.conf.Mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * 用户类
 */
@Data
public class TUser extends AbsBeanSql{
    private String  NAME;
    private String  PASSWORD;
    private Timestamp   UPTIME;
    private List<String> role;

    public TUser(Mapper mapper){
        super(mapper);
    }
    @Override
    public Integer select() {
        if(NAME==null||NAME.equals(""))
            return null;
        Integer                     selectNameCount = 0;
        //查询用户表
        lMap    = mapper.select("SELECT NAME,PASSWORD,UPTIME FROM T_USER WHERE NAME='"+NAME+"'");
        if(lMap==null||lMap.size()==0)
            return 0;
        selectNameCount = lMap.size();
        map = lMap.get(0);
        PASSWORD    = o2s("PASSWORD");
        UPTIME      = o2t("UPTIME");
        //查询用户角色
        lMap    = mapper.select("SELECT RID FROM T_USER_ROLE WHERE NAME='"+NAME+"'");
        if(lMap==null||lMap.size()==0)
            return null;
        List<Map<String,Object>>    lMapRole;
        role    = new ArrayList<>();
        for(Map<String,Object> m:lMap){
            if(m.get("RID")!=null){
                lMapRole    = mapper.select("SELECT ROLE FROM T_ROLE WHERE RID='"+m.get("RID")+"'");
                for(Map<String,Object> mRole:lMapRole){
                    if(mRole.get("ROLE")!=null){
                        role.add(mRole.get("ROLE").toString());
                    }
                }
            }
        }
        return selectNameCount;
    }

    @Override
    public Integer update() {
        if(NAME==null||NAME.equals(""))
            return null;
        if(PASSWORD!=null){
            PASSWORD    = new BCryptPasswordEncoder().encode(PASSWORD);
            UPTIME      = new Timestamp(System.currentTimeMillis());
            return mapper.update("UPDATE T_USER PASSWORD='"+PASSWORD+"',UPTIME='"+UPTIME+"' WHERE NAME='"+NAME+"'");
        }
        return 0;
    }

    @Override
    public Integer insert() {
        if(NAME==null||NAME.equals("")||PASSWORD==null||PASSWORD.equals(""))
            return null;
        UPTIME  = new Timestamp(System.currentTimeMillis());
        PASSWORD    = new BCryptPasswordEncoder().encode(PASSWORD);
        List<Map<String,Object>>    lMap    = mapper.select("SELECT PASSWORD FROM T_USER WHERE NAME='"+NAME+"'");
        if(lMap==null||lMap.size()==0)
            return mapper.insert("INSERT INTO T_USER(NAME,PASSWORD,UPTIME) VALUES('"+
                NAME+"','"+PASSWORD+"','"+UPTIME+"')");
        return 0;
    }

    @Override
    public Integer delete() {
        if(NAME==null||NAME.equals(""))
            return null;
        return mapper.delete("DELETE FROM T_USER WHERE NAME='"+NAME+"'");
    }
}
